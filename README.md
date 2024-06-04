# Repository Rudi-Portal

Bienvenue dans la documentation du repository Rudi-Portal. Ce document couvre à la fois l'objectif du portail RUDI et les instructions techniques nécessaires pour déployer la plateforme, y compris les prérequis, les microservices, le catalogue de jeux de données Dataverse et l’API Manager WSO2.

## Table des matières

1. [Introduction](#introduction)
2. [Rôle du Portail RUDI](#role-du-portail-rudi)
3. [Installation](#installation)
   - [Généralités](#généralités)
   - [Prérequis](#prérequis)
   - [Installation des microservices RUDI](#installation-des-microservices-rudi)
   - [Installation de Dataverse](#installation-de-dataverse)
   - [Installation de WSO2](#installation-de-wso2)
4. [Conclusion](#conclusion)

## Introduction

Rudi, ou Rennes Urban Data Interface, est une plateforme de partage de données à l'échelle locale, conçue pour faciliter la collaboration et la gestion des données territoriales. La métropole de Rennes a mis en place cette initiative pour permettre aux acteurs du territoire, telles que des administrations, entreprises privées, associations, chercheurs et habitants, de partager facilement leurs données tout en gardant le contrôle sur celles-ci. Cela inclut un accès en un même point à de nombreuses données, favorisant la création de services numériques basés sur une gestion responsable et éthique des données.

## Rôle du Portail RUDI

Le portail RUDI joue un rôle central dans le partage et l'accès aux données territoriales. Voici quelques-uns de ses rôles et fonctionnalités principaux :

1. **Centralisation des Données**: Le portail fournit un point d’accès unique à un large éventail de données issues de différentes sources. Cela inclut données publiques et privées des entreprises, des administrations, des associations, des chercheurs et des habitants.
   
2. **Facilitation de la Collaboration**: En regroupant diverses parties prenantes, le portail favorise le partage de données et la création de nouveaux services basés sur ces données. Les utilisateurs peuvent déclarer et gérer des réutilisations de données, des projets et des demandes d'accès spécifiques.

3. **Gestion Responsable et Éthique des Données**: Le portail respecte les réglementations en matière de protection des données (RGPD) et assure une gestion transparente et sécurisée des données.

4. **Espace Personnel**: Chaque utilisateur inscrit dispose d’un espace personnel où il peut interagir avec les données de manière sécurisée, déclarer des projets, formuler des demandes spécifiques d’accès aux données, et gérer ses propres données personnelles.

### Nœud Producteur

Le nœud producteur est une autre application open-source essentielle à Rudi, disponible dans le repository [rudi-producer-node](https://github.com/Rudi-pages-WIP/Rudi-Producer-Node). Il regroupe toutes les fonctionnalités liées à la publication de données, telles que le chargement des jeux de données et la déclaration des métadonnées. Ce nœud est généralement géré par les producteurs de données au sein de leurs systèmes d'information ou sur des plateformes cloud.

**Fonctionnalités principales** :
- **Publication de jeux de données** : Permet aux producteurs de partager leurs données avec la communauté.
- **Déclaration et gestion des métadonnées** : Facilite la catégorisation et l'indexation des données pour une recherche et une utilisation ultérieures plus efficaces.
- **Hébergement sous la responsabilité des producteurs** : Les nœuds peuvent être hébergés directement par les producteurs de données ou via des nœuds mutualisés proposés par la gouvernance de la plateforme.

## Installation

### Généralités

Le portail Rudi peut être installé de différentes manières, mais en raison du nombre élevé de microservices, une installation de type container est recommandée (par exemple, avec Kubernetes ou Docker Compose).

L'installation est composée de quatre étapes successives :

1. Installation des prérequis.
2. Installation des microservices RUDI.
3. Installation du catalogue des jeux de données Dataverse.
4. Installation de l'API Manager WSO2.

### Prérequis

Pour installer sur un système Linux Debian, les logiciels suivants doivent être installés :

- Maven
- JDK 11
- Git
- XMLStarlet

### Installation des microservices RUDI

1. **Clonage du projet**

   Cloner le projet depuis le repository suivant :

   ```bash
   git clone https://github.com/sigrennesmetropole/rudi_portal
   cd rudi_portal
   ```

2. **Construction du projet**

   Construire le projet avec Maven en utilisant la commande suivante :

   ```bash
   mvn package -Pdev-docker,prod -Dmaven.javadoc.skip=true -DskipTests
   ```

   *Remarque :* À l'issue de cette opération, les fichiers JAR des microservices seront copiés dans les répertoires de construction des images (`<root-rudi>/ci/docker`).

3. **Lancement du script d'installation**

   Exécuter le script d'installation :

   ```bash
   cd <root-rudi>/ci/docker-compose
   sudo buildDockerImage.sh
   ```

   *Remarque :* Après cette exécution, les images des différents microservices Rudi seront construites.

4. **Configuration des fichiers de propriétés**

   Éditer les fichiers de propriétés présents dans `<root-rudi>/ci/docker-compose/config/` et mettre à jour les propriétés en conséquence.

5. **Démarrage des services RUDI**

   Démarrer les services RUDI à l'aide du fichier `docker-compose.yml` fourni.

   *Remarque :*
   - Chaque microservice utilise son propre schéma de données.
   - Au démarrage, les scripts de création des tables seront exécutés automatiquement par chaque microservice.
   - Il est nécessaire de créer les rôles et schémas de chaque microservice dans la base de données.
   - Les schémas attendus sont : `acl_data`, `kalim_data`, `konsent_data`, `kos_data`, `projekt_data`, `selfdata_data`, `strukture_data`, `template_data`.

### Installation de Dataverse

L'installation de Dataverse s'appuie sur le repository `https://github.com/IQSS/dataverse-docker` dans la version `v5.5`.

1. **Clonage du projet**

   Se positionner dans le répertoire `<root-rudi>/ci/docker-compose/dataverse` :

   ```bash
   git clone https://github.com/IQSS/dataverse-docker.git
   cd dataverse-docker
   git checkout v5.5
   ```

2. **Modification des définitions des métadonnées**

   Copier les fichiers suivants pour modifier les définitions des métadonnées :

   ```bash
   cp <root-rudi>/ci/docker-compose/solr-data/collection1/conf/schema_dv_mdb_copies.xml <solr_data>/schema_dv_mdb_copies.xml
   cp <root-rudi>/ci/docker-compose/solr-data/collection1/conf/schema_dv_mdb_fields.xml <solr_data>/schema_dv_mdb_fields.xml
   cp <root-rudi>/ci/docker-compose/solr-data/collection1/conf/schema.xml <solr_data>/schema.xml
   cp <root-rudi>/ci/docker-compose/docker-compose.yml dataverse/docker-compose.yml
   ```

3. **Exécution du script dataverse**

   Exécuter le script suivant pour prendre en compte les nouvelles définitions :

   ```bash
   ./updateSchemaMDB.sh -d http://localhost:{{dataverse_port}} -t {{solr_data_conf_directory}}
   ```

### Installation de WSO2

L'installation de WSO2 s'appuie sur le repository `https://github.com/wso2/docker-apim.git` dans la version `3.2.0.1`.

1. **Clonage du projet**

   Se positionner dans le répertoire `<root-rudi>/ci/docker-compose/wso2` :

   ```bash
   git clone https://github.com/wso2/docker-apim.git
   cd docker-apim
   git checkout 3.2.0.1
   ```

2. **Création de la structure de configuration**

   Se positionner dans le répertoire `apim-with-analytics` et créer la structure suivante dans le répertoire `conf` :

   ```bash
   mkdir -p conf/apim/repository/components/dropins
   mkdir -p conf/apim/repository/components/lib
   mkdir -p conf/apim/repository/conf
   mkdir -p conf/apim/repository/deployment/server/userstores
   mkdir -p conf/apim/repository/resources/api_templates
   ```

   Copier les fichiers requis :

   ```bash
   cp <path_to_files>/org.rudi.wso2.handler.properties conf/apim
   cp <path_to_files>/org.rudi.wso2.userstore.jar conf/apim/repository/components/dropins
   cp <path_to_files>/postgresql-42.2.18.jar conf/apim/repository/components/dropins
   cp <path_to_files>/org.rudi.wso2.handler.jar conf/apim/repository/components/lib
   cp <path_to_files>/rudi-common-core.jar conf/apim/repository/components/lib
   cp <path_to_files>/rudi-facet-crypto.jar conf/apim/repository/components/lib
   cp <path_to_files>/deployment.toml conf/apim/repository/conf
   cp <path_to_files>/deployment.toml.mail conf/apim/repository/conf
   cp <path_to_files>/encryption_key.key conf/apim/repository/conf
   cp <path_to_files>/log4j2.properties conf/apim/repository/conf
   cp <path_to_files>/user-mgt.xml conf/apim/repository/conf
   cp <path_to_files>/RUDI.xml conf/apim/repository/deployment/server/userstores
   cp <path_to_files>/velocity_template.xml conf/apim/repository/resources/api_templates
   ```

   *Remarque :* Les fichiers XML, toml et properties doivent être édités pour remplacer les propriétés par les valeurs attendues.

3. **Démarrage de WSO2**

   Démarrer WSO2 à l'aide du fichier `docker-compose.yml` fourni :

   ```bash
   docker-compose -f <path_to_docker_compose>/docker-compose.yml up
   ```

## Conclusion

Vous avez maintenant toutes les informations nécessaires pour installer et configurer la plateforme RUDI. Rendez-vous sur la page [questions-et-réponses](https://github.com/orgs/Rudi-pages-WIP/discussions/categories/questions-et-r%C3%A9ponses) si vous avez des questions. 

Bon usage de Rudi !
