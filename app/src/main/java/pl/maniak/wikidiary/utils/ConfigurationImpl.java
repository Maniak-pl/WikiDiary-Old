package pl.maniak.wikidiary.utils;


public class ConfigurationImpl implements Configuration {
    @Override
    public String getApiBaseUrl() {
        return Constants.API_BASE_URL;
    }
}
