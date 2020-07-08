package com.gmail.neooxpro.lib.network.geocode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YandexGeoResponse {
    @SerializedName("response")
    private Response response;

    @NonNull
    public Response getResponse() {
        return response;
    }

    public static class Response {
        @SerializedName("GeoObjectCollection")
        private GeoObjectCollection geoObjectCollection;

        @Nullable
        public GeoObjectCollection getGeoObjectCollection() {
            return geoObjectCollection;
        }

        public static class GeoObjectCollection {
            @SerializedName("metaDataProperty")
            private MetaDataProperty metaDataProperty;
            @SerializedName("featureMember")
            private List<FeatureMember> featureMember;

            @Nullable
            public MetaDataProperty getMetaDataProperty() {
                return metaDataProperty;
            }

            @Nullable
            public List<FeatureMember> getFeatureMember() {
                return featureMember;
            }

            public static class MetaDataProperty {
                @SerializedName("GeocoderResponseMetaData")
                private GeocoderResponseMetaData geocoderResponseMetaData;

                @Nullable
                public GeocoderResponseMetaData getGeocoderResponseMetaData() {
                    return geocoderResponseMetaData;
                }

                public static class GeocoderResponseMetaData {
                    @SerializedName("request")
                    private String request;
                    @SerializedName("found")
                    private String found;
                    @SerializedName("results")
                    private String results;

                    @Nullable
                    public String getRequest() {
                        return request;
                    }

                    @Nullable
                    public String getFound() {
                        return found;
                    }

                    @Nullable
                    public String getResults() {
                        return results;
                    }

                }
            }

            public static class FeatureMember {
                @SerializedName("GeoObject")
                private GeoObject geoObject;

                @Nullable
                public GeoObject getGeoObject() {
                    return geoObject;
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
                    private Point point;

                    @Nullable
                    public MetaDataPropertyX getMetaDataProperty() {
                        return metaDataProperty;
                    }

                    @Nullable
                    public String getDescription() {
                        return description;
                    }

                    @Nullable
                    public String getName() {
                        return name;
                    }

                    @Nullable
                    public void setName(@NonNull String name) {
                        this.name = name;
                    }

                    @Nullable
                    public BoundedBy getBoundedBy() {
                        return boundedBy;
                    }

                    @Nullable
                    public Point getPoint() {
                        return point;
                    }

                    public static class MetaDataPropertyX {
                        @SerializedName("GeocoderMetaData")
                        private GeocoderMetaData geocoderMetaData;

                        @Nullable
                        public GeocoderMetaData getGeocoderMetaData() {
                            return geocoderMetaData;
                        }


                        public static class GeocoderMetaData {
                            @SerializedName("kind")
                            private String kind;
                            @SerializedName("text")
                            private String text;
                            @SerializedName("precision")
                            private String precision;
                            @SerializedName("Address")
                            private Address address;

                            @Nullable
                            public String getKind() {
                                return kind;
                            }

                            @Nullable
                            public String getText() {
                                return text;
                            }

                            @Nullable
                            public String getPrecision() {
                                return precision;
                            }

                            @Nullable
                            public Address getAddress() {
                                return address;
                            }

                            public static class Address {
                                @SerializedName("country_code")
                                private String countryCode;
                                @SerializedName("postal_code")
                                private String postalCode;
                                @SerializedName("formatted")
                                private String formatted;
                                @SerializedName("Components")
                                private List<Components> components;

                                @Nullable
                                public String getCountryCode() {
                                    return countryCode;
                                }

                                @Nullable
                                public String getPostalCode() {
                                    return postalCode;
                                }

                                @Nullable
                                public String getFormatted() {
                                    return formatted;
                                }

                                @Nullable
                                public List<Components> getComponents() {
                                    return components;
                                }

                                public static class Components {
                                    @SerializedName("kind")
                                    private String kind;
                                    @SerializedName("name")
                                    private String name;

                                    @Nullable
                                    public String getKind() {
                                        return kind;
                                    }

                                    @Nullable
                                    public String getName() {
                                        return name;
                                    }

                                    public void setName(@NonNull String name) {
                                        this.name = name;
                                    }
                                }
                            }
                        }
                    }

                    public static class BoundedBy {
                        @SerializedName("Envelope")
                        private Envelope envelope;

                        @Nullable
                        public Envelope getEnvelope() {
                            return envelope;
                        }

                        public static class Envelope {
                            @SerializedName("lowerCorner")
                            private String lowerCorner;
                            @SerializedName("upperCorner")
                            private String upperCorner;

                            @Nullable
                            public String getLowerCorner() {
                                return lowerCorner;
                            }

                            @Nullable
                            public String getUpperCorner() {
                                return upperCorner;
                            }


                        }
                    }

                    public static class Point {
                        @SerializedName("pos")
                        private String pos;

                        @Nullable
                        public String getPos() {
                            return pos;
                        }

                    }
                }
            }
        }
    }
}
