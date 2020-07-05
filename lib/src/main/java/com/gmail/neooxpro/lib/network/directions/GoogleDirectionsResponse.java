package com.gmail.neooxpro.lib.network.directions;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleDirectionsResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("routes")
    private List<Route> routes;

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public List<Route> getRoutes() {
        return routes;
    }

    public static class Route {

        @SerializedName("overview_polyline")
        private OverviewPolyLine overviewPolyLine;

        @SerializedName("legs")
        private List<Legs> legs;

        @Nullable
        public OverviewPolyLine getOverviewPolyLine() {
            return overviewPolyLine;
        }

        @Nullable
        public List<Legs> getLegs() {
            return legs;
        }

        public static class OverviewPolyLine {

            @SerializedName("points")
            private String points;

            @Nullable
            public String getPoints() {
                return points;
            }
        }

        public static class Legs {

            @SerializedName("steps")
            private List<Steps> steps;

            @Nullable
            public List<Steps> getSteps() {
                return steps;
            }

            public static class Steps {

                @SerializedName("start_location")
                private Location startLocation;

                @SerializedName("end_location")
                private Location endLocation;

                @SerializedName("polyline")
                private OverviewPolyLine polyline;

                @Nullable
                public Location getStartLocation() {
                    return startLocation;
                }

                @Nullable
                public Location getEndLocation() {
                    return endLocation;
                }

                @Nullable
                public OverviewPolyLine getPolyline() {
                    return polyline;
                }

                public static class Location {

                    @SerializedName("lat")
                    private double lat;

                    @SerializedName("lng")
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public double getLng() {
                        return lng;
                    }
                }
            }
        }
    }
}
