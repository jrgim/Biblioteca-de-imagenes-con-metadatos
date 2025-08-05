package test;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import imagenes.HelperCarpetasImagenes;

class tests {

	@Test
	void testCrearDirectorios() {
		String directorio = "src/test/directorioTest";
		HelperCarpetasImagenes.createDirectoryIfNotExists(directorio);
		assertTrue(Files.exists(Paths.get(directorio)));
		try {
			Files.deleteIfExists(Paths.get(directorio));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testCrearImagen() {
		String directoryPath = "src/test";
		String imageName = "testImage";
		HelperCarpetasImagenes.createRandomImage(directoryPath, imageName);
		assertTrue(Files.exists(Paths.get(directoryPath + "/" + imageName + ".jpeg")));
		try {
			Files.deleteIfExists(Paths.get(directoryPath + "/" + imageName + ".jpeg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
