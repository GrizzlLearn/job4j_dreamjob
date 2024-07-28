package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.repository.CityRepository;

import java.util.Collection;

/**
 * @author dl
 * @date 28.07.2024 22:12
 */
@Service
public class SimpleCityService implements CityService {
	private final CityRepository cityRepository;

	public SimpleCityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Override
	public Collection<City> findAll() {
		return cityRepository.findAll();
	}
}
