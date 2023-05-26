package br.com.certacon.certabotnfefiles.helpers;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class CarachterManipulationHelper {
    public String replaceSpecialCharacters(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalizedString.replaceAll("[^\\p{ASCII}]", "?");
    }
}
