package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.VacancyService;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author dl
 * @date 15.08.2024 20:43
 */
public class VacancyControllerTest {

	private VacancyService vacancyService;

	private CityService cityService;

	private VacancyController vacancyController;

	private MultipartFile testFile;

	@BeforeEach
	public void initServices() {
		vacancyService = mock(VacancyService.class);
		cityService = mock(CityService.class);
		vacancyController = new VacancyController(vacancyService, cityService);
		testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
	}

	@Test
	public void whenRequestVacancyListPageThenGetPageWithVacancies() {
		var vacancy1 = new Vacancy(1, "test1", "desc1", now(), true, 1, 2);
		var vacancy2 = new Vacancy(2, "test2", "desc2", now(), false, 3, 4);
		var expectedVacancies = List.of(vacancy1, vacancy2);
		when(vacancyService.findAll()).thenReturn(expectedVacancies);

		var model = new ConcurrentModel();
		var view = vacancyController.getAll(model);
		var actualVacancies = model.getAttribute("vacancies");

		assertThat(view).isEqualTo("vacancies/list");
		assertThat(actualVacancies).isEqualTo(expectedVacancies);
	}

	@Test
	public void whenRequestVacancyCreationPageThenGetPageWithCities() {
		var city1 = new City(1, "Москва");
		var city2 = new City(2, "Санкт-Петербург");
		var expectedCities = List.of(city1, city2);
		when(cityService.findAll()).thenReturn(expectedCities);

		var model = new ConcurrentModel();
		var view = vacancyController.create(model);
		var actualVacancies = model.getAttribute("cities");

		assertThat(view).isEqualTo("vacancies/create");
		assertThat(actualVacancies).isEqualTo(expectedCities);
	}

	@Test
	public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
		var expectedException = new RuntimeException("Failed to write file");
		when(vacancyService.save(any(), any())).thenThrow(expectedException);

		var model = new ConcurrentModel();
		var view = vacancyController.create(model, new Vacancy(), testFile);
		var actualExceptionMessage = model.getAttribute("message");

		assertThat(view).isEqualTo("errors/404");
		assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
	}

	@Test
	public void whenPostVacancyWithFileThenSameDataAndRedirectToVacanciesPage() throws Exception {
		var vacancy = new Vacancy(1, "test1", "desc1", now(), true, 1, 2);
		var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
		var vacancyArgumentCaptor = ArgumentCaptor.forClass(Vacancy.class);
		var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
		when(vacancyService.save(vacancyArgumentCaptor.capture(), fileDtoArgumentCaptor.capture())).thenReturn(vacancy);

		var model = new ConcurrentModel();
		var view = vacancyController.create(model, vacancy, testFile);
		var actualVacancy = vacancyArgumentCaptor.getValue();
		var actualFileDto = fileDtoArgumentCaptor.getValue();

		assertThat(view).isEqualTo("redirect:/vacancies");
		assertThat(actualVacancy).isEqualTo(vacancy);
		assertThat(fileDto).usingRecursiveComparison().isEqualTo(actualFileDto);

	}

	@Test
	public void testFindById() throws Exception {
		var vacancy1 = new Vacancy(1, "name1", "desc1", now(), true, 1, 1);
		when(vacancyService.findById(vacancy1.getId())).thenReturn(Optional.of(vacancy1));

		var model = new ConcurrentModel();
		var view = vacancyController.getById(model, vacancy1.getId());

		assertThat(view).isEqualTo("vacancies/viewEdit");
	}
}
