package io.wotkins.enums;

public enum NewsType {
    WEATHER{

        @Override
        public String getUrl() {
            return "https://world-weather.ru/pogoda/russia/saint_petersburg/";
        }
    },
    VC {
        @Override
        public String getUrl() {
            return null;
        }
    };

    public abstract String getUrl();

}
