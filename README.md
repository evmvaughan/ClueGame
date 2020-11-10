#ClueGame

## Name: Evan Vaughan
## Section: C

## Design Decisions

This game was created with a domain-driven architecture in mind. Domain objects in games typically have many interactions, so an entirely domain-driven design can become very difficult to maintain when creating more complicated games.

Nonetheless, this program features singleton services that interact with the domain entities. Each non-transient entity has a local storage context that stores all game objects in various structures. Each domain entity is (mostly) independent of other entities and can only be manipulated via services to maintain a single direction of dependency.The architecture also features a make-shift Event Bus located in SeedWork that is designed to inform the rest of the application of a major event that has happened in a given domain entity. Finally, each major subdomain of the game has a series of tests that were created for test-driven development purposes. 
