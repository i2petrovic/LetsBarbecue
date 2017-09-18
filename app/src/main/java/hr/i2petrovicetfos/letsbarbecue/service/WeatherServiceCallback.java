package hr.i2petrovicetfos.letsbarbecue.service;

import hr.i2petrovicetfos.letsbarbecue.data.Channel;


public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exeption);
}
