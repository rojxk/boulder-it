import streamlit as st
import requests
import folium
from streamlit_folium import folium_static
from streamlit.components.v1 import html

API_BASE_URL = "http://localhost:8080/api"

# Initialize session state for navigation
if 'current_view' not in st.session_state:
    st.session_state.current_view = 'areas'
if 'selected_sector_id' not in st.session_state:
    st.session_state.selected_sector_id = None
if 'nav_stack' not in st.session_state:
    st.session_state.nav_stack = ['Areas']
if 'selected_area' not in st.session_state:
    st.session_state.selected_area = None
if 'streamlit_message' in st.session_state:
    msg = st.session_state.streamlit_message
    if msg.get('type') == 'streamlit:set_lat':
        st.session_state.clicked_lat = msg['value']
    elif msg.get('type') == 'streamlit:set_lon':
        st.session_state.clicked_lon = msg['value']
    st.session_state.pop('streamlit_message')
if 'clicked_lat' not in st.session_state:
    st.session_state.clicked_lat = None
if 'clicked_lon' not in st.session_state:
    st.session_state.clicked_lon = None


def init_page():
    st.set_page_config(
        page_title="BoulderIt App",
        page_icon="🧗‍♂️",
        layout="wide"
    )


def create_map(latitude, longitude, zoom=15):
    m = folium.Map(
        location=[latitude, longitude],
        zoom_start=zoom,
        tiles='OpenStreetMap'
    )
    return m


def format_date(date_str):
    if date_str:
        from datetime import datetime
        return datetime.fromisoformat(date_str.replace('Z', '+00:00')).strftime('%Y-%m-%d %H:%M')
    return ''


def calculate_distance(lat1, lon1, lat2, lon2):
    """Calculate distance between two points in kilometers using Haversine formula"""
    from math import radians, sin, cos, sqrt, atan2

    R = 6371  # Earth's radius in kilometers

    lat1, lon1, lat2, lon2 = map(radians, [lat1, lon1, lat2, lon2])

    dlat = lat2 - lat1
    dlon = lon2 - lon1

    a = sin(dlat / 2) ** 2 + cos(lat1) * cos(lat2) * sin(dlon / 2) ** 2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))
    distance = R * c

    return distance


def navigate_to(page, **params):
    st.session_state.current_view = page
    if 'sector_id' in params:
        st.session_state.selected_sector_id = params['sector_id']
    if 'area' in params:
        st.session_state.selected_area = params['area']

    # Update navigation stack
    if page == 'areas':
        st.session_state.nav_stack = ['Areas']
    elif page == 'sector':
        if 'Areas' not in st.session_state.nav_stack:
            st.session_state.nav_stack = ['Areas', 'Sector']
        elif 'Sector' not in st.session_state.nav_stack:
            st.session_state.nav_stack.append('Sector')

    st.rerun()


def go_back():
    if len(st.session_state.nav_stack) > 1:
        st.session_state.nav_stack.pop()
        if len(st.session_state.nav_stack) == 1:
            st.session_state.current_view = 'areas'
            st.session_state.selected_sector_id = None
    st.rerun()


def display_breadcrumbs():
    col1, col2 = st.columns([1, 5])
    with col1:
        if len(st.session_state.nav_stack) > 1:
            if st.button('← Back'):
                go_back()
    with col2:
        st.write(' > '.join(st.session_state.nav_stack))


def create_parking_selection_map(area_coords, existing_parking_spots=None):
    m = folium.Map(
        location=[area_coords[0], area_coords[1]],
        zoom_start=15
    )

    # Add area marker
    folium.Marker(
        [area_coords[0], area_coords[1]],
        popup="Area location",
        icon=folium.Icon(color='red')
    ).add_to(m)

    # Add 1km radius circle
    folium.Circle(
        [area_coords[0], area_coords[1]],
        radius=1000,  # 1km in meters
        color='blue',
        fill=False,
        fillOpacity=0.2,
        popup="1km radius"
    ).add_to(m)

    # Add existing parking spots if any
    if existing_parking_spots:
        for spot in existing_parking_spots:
            p_coords = spot['coordinates']['coordinates']
            icon = folium.DivIcon(
                html="""
                    <div style='font-size:24px'>
                        🅿️
                    </div>"""
            )
            popup_content = f"""
                <div style='font-size: 14px'>
                    <h4>🅿️ {spot.get('name', 'N/A')}</h4>
                    <b>Type:</b> {spot.get('type', 'N/A')}<br>
                    <b>Capacity:</b> {spot.get('capacity', 'N/A')} cars<br>
                    <b>Description:</b> {spot.get('description', 'No description')}<br>
                </div>
            """
            folium.map.Marker(
                [p_coords[0], p_coords[1]],
                popup=folium.Popup(popup_content, max_width=300),
                tooltip=f"🅿️ {spot['name']}",
                icon=icon
            ).add_to(m)

    # Add click handler that shows coordinates
    folium.LatLngPopup().add_to(m)

    return m

def display_area_map(area, sectors=None):
    if not area or 'coordinates' not in area:
        st.error("Invalid area data")
        return

    coords = area['coordinates']['coordinates']
    m = create_map(coords[0], coords[1])

    # Add area marker
    folium.Marker(
        [coords[0], coords[1]],
        popup=area['name'],
        tooltip=area['name'],
        icon=folium.Icon(color='red')
    ).add_to(m)

    # Add parking spots
    if 'parkingSpots' in area and area['parkingSpots']:
        for spot in area['parkingSpots']:
            p_coords = spot['coordinates']['coordinates']
            popup_content = f"""
                <div style='font-size: 14px'>
                    <h4>🅿️ {spot.get('name', 'N/A')}</h4>
                    <b>Type:</b> {spot.get('type', 'N/A')}<br>
                    <b>Capacity:</b> {spot.get('capacity', 'N/A')} cars<br>
                    <b>Description:</b> {spot.get('description', 'No description')}<br>
                </div>
            """
            icon = folium.DivIcon(
                html="""
                    <div style='font-size:24px'>
                        🅿️
                    </div>"""
            )
            folium.map.Marker(
                [p_coords[0], p_coords[1]],
                popup=folium.Popup(popup_content, max_width=300),
                tooltip=f"🅿️ {spot['name']}",
                icon=icon
            ).add_to(m)

    # Add sectors
    for sector in sectors or []:
        if 'coordinates' in sector:
            s_coords = sector['coordinates']['coordinates']
            icon = folium.DivIcon(
                icon_size=[150, 36],
                icon_anchor=[0, 0],
                html='<i class="fas fa-mountain" style="font-size: 24px; color: darkgreen;"></i>'
            )
            folium.map.Marker(
                [s_coords[0], s_coords[1]],
                popup=sector['name'],
                tooltip=sector['name'],
                icon=icon
            ).add_to(m)

    folium_static(m)


def display_area_info(area):
    st.write(f"**Name:** {area.get('name', 'N/A')}")
    st.write(f"**Description:** {area.get('description', 'No description available')}")

    if 'bestSeasons' in area and area['bestSeasons']:
        st.write(f"**Best Seasons:** {', '.join(area['bestSeasons'])}")
    if 'hazards' in area and area['hazards']:
        st.write(f"**Hazards:** {', '.join(area['hazards'])}")

    # Display parking spots section
    if 'parkingSpots' in area and area['parkingSpots']:
        st.write("**Parking Spots:**")
        for spot in area['parkingSpots']:
            with st.expander(f"🅿️ {spot.get('name', 'N/A')}"):
                st.write(f"- Type: {spot.get('type', 'N/A')}")
                st.write(f"- Capacity: {spot.get('capacity', 'N/A')} cars")
                st.write(f"- Description: {spot.get('description', 'No description')}")
                handle_parking_deletion(spot, area['id'])


def handle_parking_deletion(parking_spot, area_id):
    # First ask for confirmation
    col1, col2 = st.columns([3, 1])
    with col2:
        if st.button("🗑️ Delete", key=f"delete_parking_{parking_spot['id']}"):
            # Create a unique key for this confirmation dialog
            confirm_key = f"confirm_delete_parking_{parking_spot['id']}"
            st.session_state[confirm_key] = True
            st.rerun()

    # Show confirmation dialog if triggered
    if st.session_state.get(f"confirm_delete_parking_{parking_spot['id']}", False):
        st.warning(f"Are you sure you want to delete parking spot '{parking_spot['name']}'?")
        col1, col2 = st.columns(2)
        with col1:
            if st.button("Yes, delete it", key=f"confirm_yes_{parking_spot['id']}"):
                response = requests.delete(f"{API_BASE_URL}/parking/{parking_spot['id']}?areaId={area_id}")
                if response.status_code in [200, 204]:
                    st.success("Parking spot deleted successfully!")
                    # Clear the confirmation state
                    st.session_state.pop(f"confirm_delete_parking_{parking_spot['id']}")
                    st.rerun()
                else:
                    st.error("Failed to delete parking spot")
        with col2:
            if st.button("No, keep it", key=f"confirm_no_{parking_spot['id']}"):
                # Clear the confirmation state
                st.session_state.pop(f"confirm_delete_parking_{parking_spot['id']}")
                st.rerun()


def display_sector_preview(sector):
    col1, col2 = st.columns([3, 1])
    with col2:
        if st.button('View Details', key=f"view_sector_{sector['id']}"):
            navigate_to('sector', sector_id=sector['id'])


def display_comment_content(comment):
    col1, col2 = st.columns([4, 1])
    with col1:
        st.write(f"**{comment['nickname']}**: {comment['text']}")
        st.write(f"Posted: {format_date(comment['createdAt'])}")
    with col2:
        vote_col1, vote_col2, vote_col3, flag_col = st.columns(4)
        with vote_col1:
            if st.button("👍", key=f"upvote_{comment['id']}"):
                response = requests.get(f"{API_BASE_URL}/comment/{comment['id']}/upvote")
                if response.status_code == 200:
                    st.success("Vote recorded!")
                    st.rerun()
                else:
                    st.error("Failed to vote")
        with vote_col2:
            st.write(f"{comment.get('upvotes', 0)}")
        with vote_col3:
            if st.button("👎", key=f"downvote_{comment['id']}"):
                response = requests.get(f"{API_BASE_URL}/comment/{comment['id']}/downvote")
                if response.status_code == 200:
                    st.success("Vote recorded!")
                    st.rerun()
                else:
                    st.error("Failed to vote")
        with flag_col:
            if st.button("🚩", key=f"flag_{comment['id']}"):
                response = requests.get(f"{API_BASE_URL}/comment/{comment['id']}/flag")
                if response.status_code == 200:
                    st.success("Comment flagged!")
                    st.rerun()
                else:
                    st.error("Failed to flag")
            if comment.get('flags', 0) > 0:
                st.write(f"Flags: {comment.get('flags', 0)}")
    st.write("---")


def display_comment(comment):
    is_flagged = comment.get('flags', 0) >= 5

    if is_flagged:
        if st.checkbox("🚩 Show flagged comment", key=f"show_flagged_{comment['id']}", value=False):
            display_comment_content(comment)
        else:
            st.write("---")
    else:
        display_comment_content(comment)


def display_problem(problem, comments=None):  # Add comments parameter with default None
    col1, col2 = st.columns(2)
    with col1:
        st.write(f"**Grade:** Font {problem['grades'].get('font', 'N/A')} / "
                 f"V{problem['grades'].get('vScale', 'N/A')}")

        # Grade consensus section
        st.write("**Grade Consensus:**")
        consensus = problem['grades'].get('consensus', {'harder': 0, 'easier': 0})

        # Create two columns for the consensus display
        vote_col1, vote_col2 = st.columns(2)

        with vote_col1:
            st.write(f"Harder: {consensus.get('harder', 0)}")
            if st.button("Vote Harder", key=f"harder_{problem['id']}"):
                response = requests.post(f"{API_BASE_URL}/problems/{problem['id']}/vote/harder")
                if response.status_code == 200:
                    st.success("Vote recorded!")
                    st.rerun()
                else:
                    st.error("Failed to record vote")
            st.write(f"Easier: {consensus.get('easier', 0)}")
            if st.button("Vote Easier", key=f"easier_{problem['id']}"):
                response = requests.post(f"{API_BASE_URL}/problems/{problem['id']}/vote/easier")
                if response.status_code == 200:
                    st.success("Vote recorded!")
                    st.rerun()
                else:
                    st.error("Failed to record vote")

    with col2:
        stats = problem.get('statistics', {})
        st.write("**Statistics:**")
        st.write(f"Sends: {stats.get('sendCount', 0)}")
        if st.button("I sent it!", key=f"send_{problem['id']}"):
            response = requests.post(f"{API_BASE_URL}/problems/{problem['id']}/vote/send")
            if response.status_code == 200:
                st.success("Vote recorded!")
                st.rerun()
            else:
                st.error("Failed to record vote")
        st.write(f"Projects: {stats.get('projectCount', 0)}")
        if st.button("It's my project", key=f"project_{problem['id']}"):
            response = requests.post(f"{API_BASE_URL}/problems/{problem['id']}/vote/project")
            if response.status_code == 200:
                st.success("Vote recorded!")
                st.rerun()
            else:
                st.error("Failed to record vote")
        st.write(f"Likes: {stats.get('likes', 0)}")
        if st.button("I like it 👍", key=f"like_{problem['id']}"):
            response = requests.post(f"{API_BASE_URL}/problems/{problem['id']}/vote/like")
            if response.status_code == 200:
                st.success("Vote recorded!")
                st.rerun()
            else:
                st.error("Failed to record vote")

    # Add comments section below the main problem info
    st.write("---")  # Add a separator
    st.write("**Comments:**")
    if comments and problem['id'] in comments:
        for comment in comments[problem['id']]:
            display_comment(comment)

    # Add comment form
    with st.form(key=f"comment_form_{problem['id']}"):
        nickname = st.text_input("Nickname")
        comment_text = st.text_area("Add your comment")
        if st.form_submit_button("Post Comment"):
            new_comment = {
                "problem": {
                    "id": problem['id']
                },
                "text": comment_text,
                "nickname": nickname,
                "upvotes": 0,
                "flags": 0,
                "createdAt": None  # This will be set by backend
            }
            response = requests.post(
                f"{API_BASE_URL}/comment",
                json=new_comment
            )
            if response.status_code == 201:
                st.success("Comment added!")
                st.rerun()
            else:
                st.error("Failed to add comment")


def normalize_grade(grade):
    # Remove any plus sign and uppercase the grade
    return grade.replace('+', '').upper()


def display_grade_distribution(problems):
    # Define all possible grades in order
    grade_order = ['3', '4', '5', '6A', '6B', '6C', '7A', '7B', '7C', '8A', '8B', '8C', '9A']

    # Initialize grade counts
    grade_counts = {grade: 0 for grade in grade_order}

    # Count problems for each grade
    for problem in problems:
        original_grade = problem['grades'].get('font', 'Unknown')
        normalized_grade = normalize_grade(original_grade)
        if normalized_grade in grade_counts:
            grade_counts[normalized_grade] += 1

    # Prepare data for chart
    data = {
        'Grade': list(grade_counts.keys()),
        'Count': list(grade_counts.values())
    }

    # Create columns for better layout
    col1, col2 = st.columns([3, 1])

    with col1:
        # Create bar chart
        st.bar_chart(
            data=data,
            x='Grade',
            y='Count',
            use_container_width=True
        )

    with col2:
        # Display grade statistics
        total_problems = sum(grade_counts.values())
        if total_problems > 0:
            st.write(f"**Total Problems:** {total_problems}")
            st.write("**Grade Distribution:**")
            for grade, count in grade_counts.items():
                if count > 0:
                    percentage = (count / total_problems) * 100
                    st.write(f"- {grade}: {count} ({percentage:.1f}%)")


def create_problem_page(sector_id):
    st.title("Add New Problem")

    with st.form("new_problem_form"):
        # Basic information
        name = st.text_input("Problem Name")

        # Grade selection
        col1, col2 = st.columns(2)
        with col1:
            font_grade = st.selectbox(
                "Font Grade",
                options=['3', '4', '5', '5+', '6A', '6A+', '6B', '6B+', '6C', '6C+',
                         '7A', '7A+', '7B', '7B+', '7C', '7C+', '8A', '8A+', '8B', '8B+',
                         '8C', '9A']
            )
        with col2:
            v_grade = st.selectbox(
                "V Grade",
                options=[f'V{i}' for i in range(15)]  # V0 to V14
            )

        # Type selection (multiple choice)
        problem_types = st.multiselect(
            "Problem Type",
            options=["face", "slab", "overhang", "roof", "arete", "crack"],
            default=["face"]
        )

        # Height
        height = st.number_input("Height (meters)", min_value=0.0, max_value=20.0, value=3.0, step=0.1)

        # Submit button
        if st.form_submit_button("Add Problem"):
            new_problem = {
                "sector": {
                    "id": sector_id
                },
                "name": name,
                "grades": {
                    "font": font_grade,
                    "vScale": v_grade,
                    "consensus": {
                        "harder": 0,
                        "easier": 0
                    }
                },
                "type": problem_types,
                "heightMeters": height,
                "statistics": {
                    "sendCount": 0,
                    "projectCount": 0,
                    "likes": 0
                }
            }

            response = requests.post(f"{API_BASE_URL}/problems", json=new_problem)
            if response.status_code in [200, 201]:
                st.success("Problem added successfully!")
                # Return to sector page
                st.session_state.current_view = 'sector'
                st.rerun()
            else:
                st.error("Failed to add problem")


def sector_detail_page(sector_id):
    response = requests.get(f"{API_BASE_URL}/sectors/{sector_id}")
    if response.status_code != 200:
        st.error("Failed to load sector details")
        return

    sector = response.json()
    st.title(f"Sector: {sector['name']}")

    # Get problems and display grade distribution
    problems_response = requests.get(f"{API_BASE_URL}/sectors/{sector_id}/problems")
    if problems_response.status_code == 200:
        data = problems_response.json()  # Now returns both problems and comments
        problems = data.get('problems', [])  # Get problems list
        comments = data.get('comments', {})  # Get comments dictionary

        # Add Grade Distribution section
        st.subheader("Grade Distribution")
        display_grade_distribution(problems)

        # Display individual problems
        st.subheader("Problems")
        for problem in problems:
            with st.expander(f"✔️ {problem['name']} - {problem['grades'].get('font', 'N/A')}"):
                display_problem(problem, comments)  # Pass both problem and its comments

        # Add Problem button
        if st.button("➕ Add New Problem"):
            st.session_state.current_view = 'add_problem'
            st.session_state.selected_sector_id = sector_id
            st.rerun()
    else:
        st.warning("Failed to load problems for this sector")


def add_parking_form_section(area):
    """Separate function to handle the parking form section"""
    coords = area['coordinates']['coordinates']
    area_lat = coords[0]
    area_lon = coords[1]

    st.subheader("Add New Parking Spot")
    st.write("Click on the map to see coordinates for the new parking spot:")

    # Create and display the selection map
    parking_map = create_parking_selection_map(
        [area_lat, area_lon],
        area.get('parkingSpots', [])
    )
    folium_static(parking_map)

    # Add the form below the map
    with st.form("new_parking_form"):
        parking_name = st.text_input("Parking Name")

        # Coordinate inputs
        col1, col2 = st.columns(2)
        with col1:
            lat = st.number_input("Latitude",
                                  value=st.session_state.clicked_lat if st.session_state.clicked_lat else area_lat,
                                  step=0.0001,
                                  format="%.4f",
                                  key="lat_input")
        with col2:
            lon = st.number_input("Longitude",
                                  value=st.session_state.clicked_lon if st.session_state.clicked_lon else area_lon,
                                  step=0.0001,
                                  format="%.4f",
                                  key="lon_input")

        # Show distance calculation
        distance = calculate_distance(area_lat, area_lon, lat, lon)
        if distance > 1:
            st.warning(f"⚠️ Location is {distance:.2f}km from area (max: 1km)")
        else:
            st.success(f"✅ Distance: {distance:.2f}km")

        # Parking details
        col1, col2 = st.columns([1, 2])
        with col1:
            capacity = st.number_input("Capacity (cars)", min_value=1, value=3)
            parking_type = st.selectbox("Surface Type",
                                        options=["dirt", "paved", "gravel", "grass"])
        with col2:
            description = st.text_area("Description", height=100)

        if st.form_submit_button("Add Parking Spot"):
            if distance > 1:
                st.error("Cannot add parking spot: Location is too far from the area (> 1km)")
            else:
                new_parking = {
                    "name": parking_name,
                    "coordinates": {
                        "x": lat,
                        "y": lon,
                        "type": "Point",
                        "coordinates": [lat, lon]
                    },
                    "capacity": capacity,
                    "type": parking_type,
                    "description": description,
                    "areaId": area['id']
                }

                response = requests.post(
                    f"{API_BASE_URL}/parking?areaId={area['id']}",
                    json=new_parking
                )

                if response.status_code in [200, 201]:
                    st.success("Parking spot added successfully!")
                    st.rerun()
                else:
                    st.error("Failed to add parking spot")


def areas_page():
    # Fetch all areas
    response = requests.get(f"{API_BASE_URL}/areas")
    if response.status_code != 200:
        st.error("Failed to load areas")
        return

    areas = response.json()
    if not areas:
        st.warning("No climbing areas available")
        return

    # Area selection
    selected_area_name = st.sidebar.selectbox(
        "Select Area",
        options=[area['name'] for area in areas]
    )

    selected_area = next((area for area in areas if area['name'] == selected_area_name), None)
    if not selected_area:
        return

    # Load full area details
    details_response = requests.get(f"{API_BASE_URL}/areas/{selected_area['id']}/full")
    if details_response.status_code != 200:
        st.error("Failed to load area details")
        return

    area_details = details_response.json()

    # Display area information
    col1, col2 = st.columns([2, 1])
    with col1:
        st.subheader("Area Map")
        display_area_map(
            area=area_details.get('area'),
            sectors=area_details.get('sectors')
        )

        # Add the new parking form section below the main map
        with st.expander("➕ Add Parking Spot"):
            add_parking_form_section(area_details.get('area'))

    with col2:
        st.subheader("Area Information")
        display_area_info(area_details.get('area'))

    # Display sectors
    st.subheader("Sectors")
    if sectors := area_details.get('sectors'):
        for sector in sectors:
            with st.expander(f"📍 {sector['name']}", expanded=True):
                display_sector_preview(sector)
    else:
        st.info("No sectors found for this area.")


def main():
    init_page()
    display_breadcrumbs()

    if st.session_state.current_view == 'areas':
        st.title("Climbing Areas 🏔️")
        areas_page()
    elif st.session_state.current_view == 'sector':
        sector_detail_page(st.session_state.selected_sector_id)
    elif st.session_state.current_view == 'add_problem':
        create_problem_page(st.session_state.selected_sector_id)


if __name__ == "__main__":
    main()
