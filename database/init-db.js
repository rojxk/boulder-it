use("boulderit")

// Clear existing data
db.dropDatabase();

// Create collections and indexes
db.createCollection("parking_spots");
db.createCollection("areas");
db.createCollection("sectors");
db.createCollection("problems");
db.createCollection("comments");

// Create indexes
db.areas.createIndex({ "coordinates": "2dsphere" });
db.sectors.createIndex({ "coordinates": "2dsphere" });
db.sectors.createIndex({ "area_id": 1 });
db.problems.createIndex({ "sector_id": 1 });
db.comments.createIndex({ "problem_id": 1 });

// Insert parking spot
const parkingSpotId = db.parking_spots.insertOne({
    name: "Wild Parking Czulow",
    coordinates: {
        type: "Point",
        coordinates: [50.0668, 19.6734]
    },
    description: "Wild parking spot",
    capacity: 3,
    type: "dirt",
    created_at: new Date(),
    updated_at: new Date()
}).insertedId;

//Insert areas
const zimnyDolId = db.areas.insertOne({
    name: "Zimny Dół",
    coordinates: {
        type: "Point",
        coordinates: [50.0670, 19.6752]
    },
    description: "Nice spot near Krakow",
    parking_spots: [parkingSpotId],
    hazards: ["sun exposure"],
    best_seasons: ["fall", "spring", "summer"],
    created_at: new Date(),
    updated_at: new Date()
}).insertedId;

const coronaWoodId = db.areas.insertOne({
    name: "Corona Wood",
    coordinates: {
        type: "Point",
        coordinates: [49.659546, 18.957968]
    },
    description: "Beautiful place near the mountains",
    parking_spots: [],
    hazards: ["steep peaks"],
    best_seasons: ["spring", "summer"],
    created_at: new Date(),
    updated_at: new Date()
}).insertedId;

// Insert sectors for Zimny Dol
const grupaKrowyId = db.sectors.insertOne({
    area_id: zimnyDolId,
    name: "Grupa Krowy",
    coordinates: {
        type: "Point",
        coordinates: [50.067325, 19.673841]
    },
    created_at: new Date(),
    updated_at: new Date()
}).insertedId;

// Insert sectors for Corona Wood
db.sectors.insertMany([
    {
        area_id: coronaWoodId,
        name: "1953",
        coordinates: {
            type: "Point",
            coordinates: [49.657071, 18.959299]
        },
        created_at: new Date(),
        updated_at: new Date()
    },
    {
        area_id: coronaWoodId,
        name: "Adept",
        coordinates: {
            type: "Point",
            coordinates: [49.658627, 18.960521]
        },
        created_at: new Date(),
        updated_at: new Date()
    },
    {
        area_id: coronaWoodId,
        name: "Banialuka",
        coordinates: {
            type: "Point",
            coordinates: [49.658315, 18.959221]
        },
        created_at: new Date(),
        updated_at: new Date()
    }
]);

// Insert problems for Grupa Krowy
const kermitId = db.problems.insertOne({
    sector_id: grupaKrowyId,
    name: "Kermit the frog",
    grades: {
        font: "6B+",
        v_scale: "V4",
        consensus: {
            "harder": 10,
            "easier": 5
        }
    },
    type: ["face"],
    height_meters: 3.5,
    statistics: {
        send_count: 100,
        project_count: 20,
        likes: 50
    },
    created_at: new Date(),
    updated_at: new Date()
}).insertedId;

db.problems.insertMany([
    {
        sector_id: grupaKrowyId,
        name: "Ben Moon",
        grades: {
            font: "6A",
            v_scale: "V3",
            consensus: {
                "harder": 0,
                "easier": 1
            }
        },
        type: ["face"],
        height_meters: 1.5,
        statistics: {
            send_count: 123,
            project_count: 50,
            likes: 23
        },
        created_at: new Date(),
        updated_at: new Date()
    },
    {
        sector_id: grupaKrowyId,
        name: "I shoot the sheriff",
        grades: {
            font: "6C",
            v_scale: "V5",
            consensus: {
                "harder": 5,
                "easier": 0
            }
        },
        type: ["face"],
        height_meters: 2.1,
        statistics: {
            send_count: 91,
            project_count: 12,
            likes: 88
        },
        created_at: new Date(),
        updated_at: new Date()
    },
    {
        sector_id: grupaKrowyId,
        name: "Zombie",
        grades: {
            font: "6B",
            v_scale: "V4",
            consensus: {
                "harder": 0,
                "easier": 1
            }
        },
        type: ["face"],
        height_meters: 2.2,
        statistics: {
            send_count: 221,
            project_count: 61,
            likes: 89
        },
        created_at: new Date(),
        updated_at: new Date()
    }
]);

// Insert comments
db.comments.insertOne({
    problem_id: kermitId,
    text: "Great problem! Watch out for the high crux.",
    nickname: "ClimberMagda",
    upvotes: 5,
    flags: 0,
    created_at: new Date()
});

