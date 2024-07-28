package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.City;

import java.util.Collection;

/**
 * @author dl
 * @date 28.07.2024 22:11
 */
public interface CityService {
	Collection<City> findAll();
}
