package emma.galzio.tpidlcmotorbusquedaficheros.utils;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.ModifiedToken;

public class ModifiedTokensSorter {


    public void sort(ModifiedToken[] modifiedTokens){

        quickSort( 0, modifiedTokens.length - 1 , modifiedTokens);
    }

    private void quickSort(int izq, int der, ModifiedToken[] modifiedTokens){

        int i = izq, j = der;
        ModifiedToken aux;
        ModifiedToken centro = modifiedTokens[(izq + der) / 2];
        do
        {
            while( modifiedTokens[i].getStartIndexComparator().compare(modifiedTokens[i], centro) < 0 && i < der ) { i++; }
            while( modifiedTokens[i].getStartIndexComparator().compare(modifiedTokens[i], centro) > 0 && j > izq ) { j--; }
            if( i <= j )
            {
                aux = modifiedTokens[i];
                modifiedTokens[i] = modifiedTokens[j];
                modifiedTokens[j] = aux;
                i++;
                j--;
            }
        }
        while( i <= j );
        if( izq < j ) { quickSort( izq, j , modifiedTokens); }
        if( i < der ) { quickSort( i, der, modifiedTokens ); }
    }
}


