package pl.maniak.wikidiary.utils.config;


import pl.maniak.wikidiary.utils.Constants;
import pl.maniak.wikidiary.utils.config.Configuration;

public class ConfigurationImpl implements Configuration {
    @Override
    public String getApiBaseUrl() {
        return Constants.API_BASE_URL;
    }
}
