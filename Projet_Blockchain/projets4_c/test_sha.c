/// \file test_sha.c
/// \brief fichier pour montrer comment on utilise la fonction sha256ofString
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sha256.h"
#include "sha256_utils.h"
#define STRLONG 30

int main(){
	char hashRes[65]; // contiendra le hash en hexadécimal
	
	char chaine[] = "L'éléphant lançait des avions en papier 1 2 3 123 !";
	
	sha256ofString(chaine,hashRes); // hashRes contient maintenant le hash de l'item
	
	
	int long_hash = strlen(hashRes); // sa longueur en caractères hexadécimaux
	
	printf("Le sha : %s avec %d caractères hexadécimaux, soit %d octets comme prévu\n",hashRes,long_hash,long_hash/2);
	return 0;
}
