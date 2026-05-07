# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed
- EAP bestand heeft nu een aangepaste naam met het oog op andere implementatiemodellen.
- landcode is nu onderdeel van Plaats, dit was onnodig opgesplitst naar AdministratieveEenheid.
- AdministratieveEenheid is niet meer nodig en werd uit het model gehaald.
- Datavoorbeelden bijgewerkt voor codelijsten AdministratiefBeheerdertype en ISO land codes.
- Attributen van de VDIP Envelop gecorrigeerd.

### Fixed
- VOCAB BIO URIs aangepast naar eigen URIs die al reeds beschikbaar waren in het Vocabularium Persoon.
- Persoonsrelatie en Inwonerschap associatieklasse URIs aangepast zodat ze herbruik zijn.
- Alle afkortingen zijn ge-expandeerd in de definities en gebruiksnotas voor een hogere leesbaarheid.
- Validation unittest gegeneerde bestanden uit de repository gehaald.
- URI van superklasse LocatiePeriodeOfJurisdictie gecorigeerd.
- URIs die uit Persoon vocabulary moeten komen aangepast door verhuis naar implementatie.data.vlaanderen.be
- Codelijsten gelinkt in HTML spec.

## [0.0.4] - 2026-04-30

### Added
- Optionele HTTP header `X-Bron` in Swagger om de afnemer te laten kiezen tussen Rijkregister of KSZ als databron.
- Optionele HTTP header `X-ExtraIT` in Swagger om afnemer extra data te laten ophalen. Bijvoorbeeld: Afstamming bij KSZ.

### Changed
- Release artefacts zijn nu beschikbaar in [resources/OSLO-Persoon-IM/artefacts](resources/OSLO-Persoon-IM/artefacts) om te kunnen versioneren.

## [0.0.3] - 2026-04-22

### Added
- Unittest validatie project dat valideert of de Swagger en de datavoorbeelden matchen en valideren.

### Changed
- @id is enkel verplicht bij objecten of codelijsten die een PURI hebben, toegepast op Swagger en datavoorbeelden.
- Kardinaliteiten aangepast van Versie.creatiedatum en Versie.bron voor filtering.

### Fixed
- Titel Vocabularium en Implementatiemodel volgen nu de syntaxregels.
- Publicatiedatum
- Stakeholder lijst is aangepast volgens het Implementatiemodel ipv Applicatieprofiel.
- test.data.vlaanderen.be URIs vervangen door implementatie.data.vlaanderen.be URIs vooraleer naar productie te gaan.

## [0.0.2] - 2026-04-21

### Fixed
- Configuratienaam zodat Content-Negotiation actief is.

## [0.0.1] - 2026-04-20

### Added
- Initiële release
