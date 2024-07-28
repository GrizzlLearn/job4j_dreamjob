package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.City;

import java.util.Collection;

/**
 * @author dl
 * @date 28.07.2024 22:08
 */
public interface CityRepository {
	Collection<City> findAll();
}
