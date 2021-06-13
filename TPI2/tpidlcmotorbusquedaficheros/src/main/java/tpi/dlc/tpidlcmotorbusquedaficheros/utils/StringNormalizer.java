package tpi.dlc.tpidlcmotorbusquedaficheros.utils;

import java.text.Normalizer;

public class StringNormalizer {

    public String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
