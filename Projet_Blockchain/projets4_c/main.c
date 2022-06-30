#include "main.h"


char* getTimeStamp(){
  time_t ltime;
  time(&ltime);
  return ctime(&ltime);
}

void affichageBlock(Block b){
  printf("numBlock            : %d\n",b.numBlock);
  printf("timestamp           : ");
  for (int i=0 ; i<strlen(b.timestamp)-1; i++)
    printf("%c",b.timestamp[i]);
  printf("\n");
  printf("hashBlock_precedent : %s\n",b.hashBlock_precedent);
  printf("nbTransaction       : %d\n",b.nbTransaction);
  printf("hashRoot            : %s\n",b.hashRoot);
  printf("nonce               : %d\n",b.nonce);
  printf("hashBlock           : %s\n",b.hashBlock);
  printf("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n\n");
}

char* BlockToString(Block b){
  static char string[1000] = "";
  sprintf(string,"%d %s %s %d %s %d",b.numBlock, b.timestamp, b.hashBlock_precedent, b.nbTransaction, b.hashRoot, b.nonce);
  return string;
}

char* MerkleTreeRoot(Block *b){
  int nbRoot = b->nbTransaction;

  //creation tableu à 2 dimension 
  char **listeRoot = malloc(nbRoot * sizeof(char*));
  if (listeRoot == NULL) // On vérifie si la mémoire a été allouée
    exit(0); // Erreur : on arrête tout !
  for (int i=0 ; i<nbRoot ; i++){
    listeRoot[i] = malloc(65 * sizeof(char));
    if (listeRoot[i] == NULL) // On vérifie si la mémoire a été allouée
      exit(0); // Erreur : on arrête tout !
  }

  //1er parcours pour transformer les transaction en hash
  for (int i=0 ; i<nbRoot ; i++){
    sha256ofString((BYTE *)b->listeTransaction[i].message, listeRoot[i]);
    //printf("\nstring : %s\n", b->listeTransaction[i].message);
    //printf("Le sha : %s \n",listeRoot[i]);
  }

  //boucle qui calcule le root du block
  while (nbRoot !=1){
    for (int i=0 ; i<nbRoot-nbRoot%2 ; i+=2){
        char temp[129] = "";
        sprintf(temp,"%s%s",listeRoot[i], listeRoot[i+1]);
        sha256ofString((BYTE *)temp, listeRoot[i/2]);
        //printf("m: %s\n", temp);
        //printf("Le sha : %s \n\n",listeRoot[i/2]);
    }

    if (nbRoot%2 == 1){
      for (int i=0 ; i<65 ; i++)
        listeRoot[nbRoot/2][i] = listeRoot[nbRoot-1][i];
      //printf("le sha : %s \n",listeRoot[nbRoot/2]);
      nbRoot = nbRoot/2+1;
    }
    else
      nbRoot /=2;
    //printf("---------------------nbRoot: %d\n",nbRoot);
  }

  static char hashRoot[65];
  //copie du resultat
  strcpy(hashRoot, listeRoot[0]);

  //libération mémoire
  for (int i=0 ; i<b->nbTransaction ; i++)
    free(listeRoot[i]);
  free(listeRoot); 

  return hashRoot;
}

void creationBlockGenesis (Blockchain *blockchain){
  // numéro du block
  blockchain->listeBlock[blockchain->nbBlock].numBlock = blockchain->nbBlock;
  // date au moment de la création
  blockchain->listeBlock[blockchain->nbBlock].timestamp = getTimeStamp();;
  // hash du block précédent
  blockchain->listeBlock[blockchain->nbBlock].hashBlock_precedent = "0";
  // nombre de transaction
  blockchain->listeBlock[blockchain->nbBlock].nbTransaction = 0;
  // nonce
  blockchain->listeBlock[blockchain->nbBlock].nonce = 0;
  // hash Block
  sha256ofString((BYTE *)BlockToString(blockchain->listeBlock[0]),blockchain->listeBlock[0].hashBlock);
  
  blockchain->nbBlock++;
}

void initialisationBlock (Block *block, Blockchain *blockchain){
  // numéro du block
  block->numBlock = blockchain->nbBlock;
  // date au moment de la création
  block->timestamp = getTimeStamp();
  // hash du block précédent
  block->hashBlock_precedent = blockchain->listeBlock[blockchain->nbBlock-1].hashBlock;
  // nombre de transaction
  block->nbTransaction = 0;
  // nonce
  block->nonce = 0;
}

void ajouterBlock(Block block, Blockchain *blockchain){
  blockchain->nbBlock ++;
  blockchain->listeBlock = realloc(blockchain->listeBlock, blockchain->nbBlock * sizeof(struct Block));
  blockchain->listeBlock[blockchain->nbBlock-1] = block;
}

void choixTransaction(TransactionGlobal *transactionGlobal, Transaction *listeTransaction, int *nbTransaction){
  *nbTransaction = rand()%NB_TRANSCATION_MAX+1;
  printf("le mineur prend %d transaction dans la liste de transcation global\n", *nbTransaction);
  // on verifie qu'il y a assez de transaction en stock sinon on reduit nbTransaction
  if ((transactionGlobal->iLastTransacUse+*nbTransaction) > transactionGlobal->iTransactionGlobal){
    *nbTransaction = transactionGlobal->iTransactionGlobal - transactionGlobal->iLastTransacUse;
    printf("pas assez de transaction il prend donc %d transaction\n",*nbTransaction);
  }
  
  if (*nbTransaction != 0){
    transactionGlobal->iLastTransacUse++;
    //on ajoute les transaction à mettre dans le bloc dans une nouvelle liste de transaction independante
    for (int i=0 ; i<*nbTransaction ; i++){
      listeTransaction[i] = transactionGlobal->listeTransactionGlobal[transactionGlobal->iLastTransacUse+i];
    }
    // on affiche les transaction prit par le mieur
    for (int i=0 ; i<*nbTransaction ; i++)
      printf("t %d ║ %s\n",transactionGlobal->iLastTransacUse+i,listeTransaction[i].message);
    printf("\n");
    // on actualise l'indice de la dernier transaction utilisé
    transactionGlobal->iLastTransacUse = transactionGlobal->iLastTransacUse+*nbTransaction-1;
  }
  else
    printf("plus de transaction dispo\n");
}

void miner(Blockchain *blockchain, TransactionGlobal *transactionGlobal, char *mineur){
  int         conditionExit = 0;
  int         nbTransaction = 0;
  Transaction listeTransaction[10];

  choixTransaction(transactionGlobal, listeTransaction, &nbTransaction);

  if (nbTransaction != 0){
    Block block;
    initialisationBlock(&block, blockchain);
    block.nbTransaction = nbTransaction;
    block.listeTransaction = listeTransaction;    
    block.hashRoot = MerkleTreeRoot(&block);

    while (!conditionExit){
      conditionExit = 1;
      sha256ofString((BYTE *)BlockToString(block),block.hashBlock);
      
      //printf("\nnonce: %d",b->nonce);
      //printf("%s\n\n",string);
      //printf("\nLe sha : %s \n",b->hashBlock);

      block.nonce++;

      for (int i=0 ; i<blockchain->difficulte ; i++){
        if (block.hashBlock[i] != '0')
          conditionExit = 0;
      }
    }
    block.nonce--;  
    printf("le mineur %s créé le bloc n°%d\n",mineur,block.numBlock);
    affichageBlock(block);
    ajouterBlock(block, blockchain);
  }
  else
    printf("plus de transaction dispo le mineur ne peut pas creer de block");
}

void randomPrenom(Utilistateur *listUtilisateur, int nbUtilisateur){
  char liste[20][3] = {"ca","mi","le","ke","vi","ma","th","eu","na","de","ge","lu","ci","ca","re","ar","an","so","ni","to"};
  
  for (int i=1 ; i<nbUtilisateur ; i++){
    int occurance = rand()%(5-2)+2;

    for (int j=0 ; j<occurance ; j++)
      strcat(listUtilisateur[i].prenom,liste[rand()%20]);
  }
}

void creationTransaction(Block *b,int montant,char *emeteur, char *receveur){  
  sprintf(b->listeTransaction[b->nbTransaction].message,"Source: %s -> Destination: %s, montant: %d",emeteur, receveur,montant);
  b->nbTransaction++;
}

void GenerateurTransactions(TransactionGlobal *transactionGlobal, Utilistateur *listUtilisateur,int nbUtilisateur, int nbTransaction){

  for (int i=0 ; i<nbTransaction ; i++){
    char *emeteur;
    char *receveur;

    do{
      emeteur  = listUtilisateur[rand()%nbUtilisateur].prenom;
      receveur = listUtilisateur[rand()%nbUtilisateur].prenom;
    } while (!strcmp(emeteur,receveur));

    int montant = rand()%MONTANT_MAX+1;

    transactionGlobal->iTransactionGlobal++;
    sprintf(transactionGlobal->listeTransactionGlobal[transactionGlobal->iTransactionGlobal].message,"Source: %s -> Destination: %s, montant: %d",emeteur, receveur,montant);
    //printf("transaction %d: %s\n",transactionGlobal->iTransactionGlobal,transactionGlobal->listeTransactionGlobal[transactionGlobal->iTransactionGlobal].message);
  }

}

void valideGenesis(Blockchain *blockchain){
  if (blockchain->listeBlock[0].numBlock)
    fprintf(stderr,"ERREUR avec le block genesis\n");
}

void valideChainageHashBlock(Blockchain *blockchain){
  for (int i=1 ; i<blockchain->nbBlock ; i++){
    if (strcmp(blockchain->listeBlock[i-1].hashBlock, blockchain->listeBlock[i].hashBlock_precedent)){
      fprintf(stderr,"ERREUR block %d le chainage ne correspond pas\n",i);
      fprintf(stderr,"block %d, hashBlock           : %s\n",i-1,blockchain->listeBlock[i-1].hashBlock);
      fprintf(stderr,"block %d, hashBlock_precedent : %s\n\n",i,blockchain->listeBlock[i].hashBlock_precedent);
    }
  }
}

void valideHashBlock(Blockchain *blockchain){
  for (int i=0 ; i<blockchain->nbBlock ; i++){
    char tempHashBlock[65];
    sha256ofString((BYTE *)BlockToString(blockchain->listeBlock[i]),tempHashBlock);

    if (strcmp(tempHashBlock, blockchain->listeBlock[i].hashBlock)){
      fprintf(stderr,"ERREUR block %d le hash du block est FAUX\n",i);
      fprintf(stderr,"hashBlock annoncé: %s\n",blockchain->listeBlock[i].hashBlock);
      fprintf(stderr,"hashBlock calculé: %s\n\n",tempHashBlock);
    }
  }
}

void valideHashaMerkleTreeRoot(Blockchain *blockchain){
  for (int i=1 ; i<blockchain->nbBlock ; i++){
    char *tempHashRoot = MerkleTreeRoot(&blockchain->listeBlock[i]);
    
    if (strcmp(tempHashRoot, blockchain->listeBlock[i].hashRoot)){
      fprintf(stderr,"ERREUR block %d le hashRoot du block est FAUX\n",i);
      fprintf(stderr,"hashRoot annoncé: %s\n",blockchain->listeBlock[i].hashRoot);
      fprintf(stderr,"hashRoot calculé: %s\n\n",tempHashRoot);
    }
  }
}

void verificationBlockChain(Blockchain *blockchain){
  valideGenesis(blockchain);
  valideChainageHashBlock(blockchain);
  /*
  cette fontion ne marche plus et j'ai aucune idéée pourquoi..
  valideHashBlock(blockchain);
  */
  valideHashaMerkleTreeRoot(blockchain);
  printf("vérification de la blockchain terminé.\n");
}

void HelicopterMoney(Utilistateur *listUser, int nbUtilisateur, TransactionGlobal *transactionGlobal ){
  printf("Phase helicoptére money\n");
  for (int i=1 ; i<nbUtilisateur; i++){
    transactionGlobal->iTransactionGlobal++;
    sprintf(transactionGlobal->listeTransactionGlobal[transactionGlobal->iTransactionGlobal].message,"Source: Coinbase -> Destination: %s, montant: %d", listUser[i].prenom, 50);
    printf("%d %s\n",transactionGlobal->iTransactionGlobal,transactionGlobal->listeTransactionGlobal[transactionGlobal->iTransactionGlobal].message);
  }
  printf("\n");
}

int main(int argc, char* argv[]){

  if (argc != 4) {
    fprintf(stderr, "%s difficulté nbBlockMiner nbUtilisateur\n", argv[0]);
    exit(1);
  }
  
  int difficulte    = atoi(argv[1]);
  int nbBlockMiner  = atoi(argv[2]);
  int nbUtilisateur = atoi(argv[3]);

  if (difficulte<1 || nbBlockMiner<1 || nbUtilisateur<2 ) {
    fprintf(stderr, "%s difficulté nbBlockMiner nbUtilisateur\n", argv[0]);
    fprintf(stderr, "difficulté    >= 1\n");
    fprintf(stderr, "nbBlockMiner  >= 1\n");
    fprintf(stderr, "nbUtilisateur >= 2\n");
    exit(2);
  }

  // ~~~~~~~~~~~~~ INITIALISATION ~~~~~~~~~~~~~ 
  srand(time(NULL));
  //creation liste des blocks
  Block *listeBlock = malloc(1 * sizeof(Block));
  if (listeBlock == NULL) // On vérifie si la mémoire a été allouée
    exit(0); // Erreur : on arrête tout !

  Blockchain        blockchain = {difficulte,0,listeBlock}; // creation blockchain
  Transaction       listeTransactionGlobal[1000]; // création liste de transaction global
  TransactionGlobal transactionGlobal = {listeTransactionGlobal, -1, -1}; // création de la structure de transaction global
  
  //creation liste utilisateur
  Utilistateur *listUtilisateur = malloc(nbUtilisateur * sizeof(Utilistateur));
  if (listUtilisateur == NULL) // On vérifie si la mémoire a été allouée
    exit(0); // Erreur : on arrête tout !

  // ajout utilisateur Creator
  sscanf("Creator","%s",listUtilisateur[0].prenom);

  // ajout d'utilisateur random
  randomPrenom(listUtilisateur, nbUtilisateur);

  // affichage des utilisateur
  for (int i=0 ; i<nbUtilisateur ; i++)
    printf("Utilistateur %d: %s\n",i+1,listUtilisateur[i].prenom);
  printf("\nAppuyez sur Entrée...\n\n");
  getchar();
  // ~~~~~~ FIN INITIALISATION ~~~~~~~ 

  // ~~ BCB.1: création genesis par creator
  creationBlockGenesis(&blockchain);
  affichageBlock(blockchain.listeBlock[blockchain.nbBlock-1]);

  // ~~ BCB.2: Phase "Helicopter money"
  HelicopterMoney(listUtilisateur, nbUtilisateur, &transactionGlobal );

  // ~~ BCB.3: transaction aleatoire
  // on génere 10 transaction avant chaque création de block pour etre tranquille
  GenerateurTransactions(&transactionGlobal, listUtilisateur, nbUtilisateur, 10);

  // ~~ BCB.4: on choisi un mineur au hasard
  char *mineur = listUtilisateur[rand()%nbUtilisateur].prenom;
  
  // minage
  for (int i=0 ; i<nbBlockMiner ; i++){
    // on génere 10 transaction avant chaque création de block pour etre tranquille
    GenerateurTransactions(&transactionGlobal, listUtilisateur, nbUtilisateur, 10);

    miner(&blockchain, &transactionGlobal, mineur);
    
    // verification de la block-chain
    verificationBlockChain(&blockchain);
  }
  
  //libération mémoire
  //free(listeBlock);
  free(listUtilisateur); 

  return 0;
}