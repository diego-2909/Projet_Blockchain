#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include "sha256_utils.h"


#define NB_TRANSCATION_MAX 10
#define MONTANT_MAX 100000
#define SIZE_PRENOM 8

struct Transaction {
  char message[100];
};
typedef struct Transaction Transaction;

struct Block{
  int   numBlock;
  char *timestamp; // date de création
  char *hashBlock_precedent;
  int   nbTransaction;
  int   nonce; // difficulté
  Transaction *listeTransaction; // liste des transaction
  char *hashRoot; // hash root de l’arbre de Merkle des transactions 
  char  hashBlock[65];
};
typedef struct Block Block;

struct Blockchain{
  int    difficulte;
  int    nbBlock;
  Block *listeBlock;
};
typedef struct Blockchain Blockchain;

struct Utilistateur{
  char prenom[SIZE_PRENOM+1];
};
typedef struct Utilistateur Utilistateur;

struct TransactionGlobal{
  Transaction *listeTransactionGlobal;
  int iTransactionGlobal;
  int iLastTransacUse;
};
typedef struct TransactionGlobal TransactionGlobal;


char* getTimeStamp();
void  affichageBlock(Block b);
char* BlockToString(Block b);
char* MerkleTreeRoot(Block *b);
void  creationBlockGenesis (Blockchain *blockchain);
void  initialisationBlock (Block *block, Blockchain *blockchain);
void  ajouterBlock(Block block, Blockchain *blockchain);
void  choixTransaction(TransactionGlobal *transactionGlobal, Transaction *listeTransaction, int *nbTransaction);
void  miner(Blockchain *blockchain, TransactionGlobal *transactionGlobal, char *mineur);
void  creationTransaction(Block *b,int montant,char *emeteur, char *receveur);
void  randomPrenom(Utilistateur *listUtilisateur, int nbUtilisateur);
void  GenerateurTransactions(TransactionGlobal *transactionGlobal, Utilistateur *listUtilisateur,int nbUtilisateur, int nbTransaction);
void  valideGenesis(Blockchain *blockchain);
void  valideChainageHashBlock(Blockchain *blockchain);
void  valideHashBlock(Blockchain *blockchain);
void  valideHashaMerkleTreeRoot(Blockchain *blockchain);
void  verificationBlockChain(Blockchain *blockchain);