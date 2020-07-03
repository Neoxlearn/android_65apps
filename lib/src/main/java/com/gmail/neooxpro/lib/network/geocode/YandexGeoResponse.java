package com.gmail.neooxpro.lib.network.geocode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YandexGeoResponse {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        @SerializedName("GeoObjectCollection")
        private GeoObjectCollection GeoObjectCollection;

        public GeoObjectCollection getGeoObjectCollection() {
            return GeoObjectCollection;
        }

        public void setGeoObjectCollection(GeoObjectCollection GeoObjectCollection) {
            this.GeoObjectCollection = GeoObjectCollection;
        }

        public static class GeoObjectCollection {
            @SerializedName("metaDataProperty")
            private MetaDataProperty metaDataProperty;
            @SerializedName("featureMember")
            private List<FeatureMember> featureMember;

            public MetaDataProperty getMetaDataProperty() {
                return metaDataProperty;
            }

            public void setMetaDataProperty(MetaDataProperty metaDataProperty) {
                this.metaDataProperty = metaDataProperty;
            }

            public List<FeatureMember> getFeatureMember() {
                return featureMember;
            }

            public void setFeatureMember(List<FeatureMember> featureMember) {
                this.featureMember = featureMember;
            }

            public static class MetaDataProperty {
                @SerializedName("GeocoderResponseMetaData")
                private GeocoderResponseMetaData GeocoderResponseMetaData;

                public GeocoderResponseMetaData getGeocoderResponseMetaData() {
                    return GeocoderResponseMetaData;
                }

                public void setGeocoderResponseMetaData(GeocoderResponseMetaData GeocoderResponseMetaData) {
                    this.GeocoderResponseMetaData = GeocoderResponseMetaData;
                }

                public static class GeocoderResponseMetaData {
                    @SerializedName("request")
                    private String request;
                    @SerializedName("found")
                    private String found;
                    @SerializedName("results")
                    private String results;

                    public String getRequest() {
                        return request;
                    }

                    public void setRequest(String request) {
                        this.request = request;
                    }

                    public String getFound() {
                        return found;
                    }

                    public void setFound(String found) {
                        this.found = found;
                    }

                    public String getResults() {
                        return results;
                    }

                    public void setResults(String results) {
                        this.results = results;
                    }
                }
            }

            public static class FeatureMember {
                @SerializedName("GeoObject")
                private GeoObject GeoObject;

                public GeoObject getGeoObject() {
                    return GeoObject;
                }

                public void setGeoObject(GeoObject GeoObject) {
                    this.GeoObject = GeoObject;
                }

                public static class GeoObject {
                    @SerializedName("metaDataProperty")
                    private MetaDataPropertyX metaDataProperty;
                    @SerializedName("description")
                    private String description;
                    @SerializedName("name")
                    private String name;
                    @SerializedName("boundedBy")
                    private BoundedBy boundedBy;
                    @SerializedName("Point")
                    private Point Point;

                    public MetaDataPropertyX getMetaDataProperty() {
                        return metaDataProperty;
                    }

                    public void setMetaDataProperty(MetaDataPropertyX metaDataProperty) {
                        this.metaDataProperty = metaDataProperty;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public BoundedBy getBoundedBy() {
                        return boundedBy;
                    }

                    public void setBoundedBy(BoundedBy boundedBy) {
                        this.boundedBy = boundedBy;
                    }

                    public Point getPoint() {
                        return Point;
                    }

                    public void setPoint(Point Point) {
                        this.Point = Point;
                    }

                    public static class MetaDataPropertyX {
                        @SerializedName("GeocoderMetaData")
                        private GeocoderMetaData GeocoderMetaData;

                        public GeocoderMetaData getGeocoderMetaData() {
                            return GeocoderMetaData;
                        }

                        public void setGeocoderMetaData(GeocoderMetaData GeocoderMetaData) {
                            this.GeocoderMetaData = GeocoderMetaData;
                        }

                        public static class GeocoderMetaData {
                            @SerializedName("kind")
                            private String kind;
                            @SerializedName("text")
                            private String text;
                            @SerializedName("precision")
                            private String precision;
                            @SerializedName("Address")
                            private Address Address;

                            public String getKind() {
                                return kind;
                            }

                            public void setKind(String kind) {
                                this.kind = kind;
                            }

                            public String getText() {
                                return text;
                            }

                            public void setText(String text) {
                                this.text = text;
                            }

                            public String getPrecision() {
                                return precision;
                            }

                            public void setPrecision(String precision) {
                                this.precision = precision;
                            }

                            public Address getAddress() {
                                return Address;
                            }

                            public void setAddress(Address Address) {
                                this.Address = Address;
                            }

                            public static class Address {
                                @SerializedName("country_code")
                                private String countryCode;
                                @SerializedName("postal_code")
                                private String postalCode;
                                @SerializedName("formatted")
                                private String formatted;
                                @SerializedName("Components")
                                private List<Components> Components;

                                public String getCountryCode() {
                                    return countryCode;
                                }

                                public void setCountryCode(String countryCode) {
                                    this.countryCode = countryCode;
                                }

                                public String getPostalCode() {
                                    return postalCode;
                                }

                                public void setPostalCode(String postalCode) {
                                    this.postalCode = postalCode;
                                }

                                public String getFormatted() {
                                    return formatted;
                                }

                                public void setFormatted(String formatted) {
                                    this.formatted = formatted;
                                }

                                public List<Components> getComponents() {
                                    return Components;
                                }

                                public void setComponents(List<Components> Components) {
                                    this.Components = Components;
                                }

                                public static class Components {
                                    @SerializedName("kind")
                                    private String kind;
                                    @SerializedName("name")
                                    private String name;

                                    public String getKind() {
                                        return kind;
                                    }

                                    public void setKind(String kind) {
                                        this.kind = kind;
                                    }

                                    public String getName() {
                                        return name;
                                    }

                                    public void setName(String name) {
                                        this.name = name;
                                    }
                                }
                            }
                        }
                    }

                    public static class BoundedBy {
                        @SerializedName("Envelope")
                        private Envelope Envelope;

                        public Envelope getEnvelope() {
                            return Envelope;
                        }

                        public void setEnvelope(Envelope Envelope) {
                            this.Envelope = Envelope;
                        }

                        public static class Envelope {
                            @SerializedName("lowerCorner")
                            private String lowerCorner;
                            @SerializedName("upperCorner")
                            private String upperCorner;

                            public String getLowerCorner() {
                                return lowerCorner;
                            }

                            public void setLowerCorner(String lowerCorner) {
                                this.lowerCorner = lowerCorner;
                            }

                            public String getUpperCorner() {
                                return upperCorner;
                            }

                            public void setUpperCorner(String upperCorner) {
                                this.upperCorner = upperCorner;
                            }
                        }
                    }

                    public static class Point {
                        @SerializedName("pos")
                        private String pos;

                        public String getPos() {
                            return pos;
                        }

                        public void setPos(String pos) {
                            this.pos = pos;
                        }
                    }
                }
            }
        }
    }
}
