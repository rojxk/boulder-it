package com.boulderit;

import com.boulderit.model.Area;
import com.boulderit.model.ParkingSpot;
import com.boulderit.model.Problem;
import com.boulderit.model.Sector;
import com.boulderit.repository.AreaRepository;
import com.boulderit.repository.ParkingSpotRepository;
import com.boulderit.repository.ProblemRepository;
import com.boulderit.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BoulderitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoulderitApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ParkingSpotRepository parkingSpotRepository, AreaRepository areaRepository, SectorRepository sectorRepository, ProblemRepository problemRepository){
		return runner -> {
			printParkingSpot(parkingSpotRepository);
			printArea(areaRepository);
			printSector(sectorRepository);
			printProblem(problemRepository);

		};
	}

	private void printProblem(ProblemRepository problemRepository) {
		System.out.println("Printing problem");
		List<Problem> problems = problemRepository.findAll();
		System.out.println(problems);
	}

	private void printSector(SectorRepository sectorRepository) {
		System.out.println("Printing sector");
		List<Sector> sectors = sectorRepository.findAll();
		System.out.println(sectors);
	}

	private void printArea(AreaRepository areaRepository) {
		System.out.println("Printing area");
		List<Area> areas = areaRepository.findAll();
		System.out.println(areas);
	}

	private void printParkingSpot(ParkingSpotRepository parkingSpotRepository) {
		System.out.println("Printing parking spot");

		List<ParkingSpot> parkingSpotList = parkingSpotRepository.findAll();

		System.out.println(parkingSpotList);
	}


}
