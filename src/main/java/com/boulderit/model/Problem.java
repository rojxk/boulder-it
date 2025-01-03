package com.boulderit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document("problems")
public class Problem {
    @Id
    private String id;
    @Field("created_at")
    private Date createdAt;
    private Grades grades;
    @Field("height_meters")
    private Double heightMeters;
    private String name;
    @DocumentReference
    @Field("sector_id")
    private Sector sector;
    private List<String> type;
    @Field("updated_at")
    private Date updatedAt;
    private Statistics statistics;


    public static class Grades {
        private String font;
        @Field("v_scale")
        private String vScale;
        private Consensus consensus;

        public Grades(){

        }

        public Grades(String font, String vScale, Consensus consensus) {
            this.font = font;
            this.vScale = vScale;
            this.consensus = consensus;
        }

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }

        public String getvScale() {
            return vScale;
        }

        public void setvScale(String vScale) {
            this.vScale = vScale;
        }

        public Consensus getConsensus() {
            return consensus;
        }

        public void setConsensus(Consensus consensus) {
            this.consensus = consensus;
        }

        @Override
        public String toString() {
            return "Grades{" +
                    "font='" + font + '\'' +
                    ", vScale='" + vScale + '\'' +
                    ", consensus=" + consensus +
                    '}';
        }

        public static class Consensus{
            private Integer harder;
            private Integer easier;

            public Consensus(){

            }

            public Consensus(Integer harder, Integer easier) {
                this.harder = harder;
                this.easier = easier;
            }

            public Integer getHarder() {
                return harder;
            }

            public void setHarder(Integer harder) {
                this.harder = harder;
            }

            public Integer getEasier() {
                return easier;
            }

            public void setEasier(Integer easier) {
                this.easier = easier;
            }

            @Override
            public String toString() {
                return "Consensus{" +
                        "harder=" + harder +
                        ", easier=" + easier +
                        '}';
            }
        }


    }



    public static class Statistics {
        @Field("send_count")
        private Integer sendCount;
        @Field("project_count")
        private Integer projectCount;
        private Integer likes;

        public Statistics() {
        }

        public Statistics(Integer sendCount, Integer projectCount, Integer likes) {
            this.sendCount = sendCount;
            this.projectCount = projectCount;
            this.likes = likes;
        }

        public Integer getSendCount() {
            return sendCount;
        }

        public void setSendCount(Integer sendCount) {
            this.sendCount = sendCount;
        }

        public Integer getProjectCount() {
            return projectCount;
        }

        public void setProjectCount(Integer projectCount) {
            this.projectCount = projectCount;
        }

        public Integer getLikes() {
            return likes;
        }

        public void setLikes(Integer likes) {
            this.likes = likes;
        }

        @Override
        public String toString() {
            return "Statistics{" +
                    "sendCount=" + sendCount +
                    ", projectCount=" + projectCount +
                    ", likes=" + likes +
                    '}';
        }
    }

    public Problem(){

    }

    public Problem(String id, Date createdAt, Grades grades, Double heightMeters, String name, Sector sector, List<String> type, Date updatedAt, Statistics statistics) {
        this.id = id;
        this.createdAt = createdAt;
        this.grades = grades;
        this.heightMeters = heightMeters;
        this.name = name;
        this.sector = sector;
        this.type = type;
        this.updatedAt = updatedAt;
        this.statistics = statistics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Grades getGrades() {
        return grades;
    }

    public void setGrades(Grades grades) {
        this.grades = grades;
    }

    public Double getHeightMeters() {
        return heightMeters;
    }

    public void setHeightMeters(Double heightMeters) {
        this.heightMeters = heightMeters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", grades=" + grades +
                ", heightMeters=" + heightMeters +
                ", name='" + name + '\'' +
                ", sector=" + sector +
                ", type=" + type +
                ", updatedAt=" + updatedAt +
                ", statistics=" + statistics +
                '}';
    }
}
