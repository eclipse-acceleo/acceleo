var data = {
    "rooms": [
        {
            "name": "firstRoom",
            "states": {
                "default": {
                    "description": "You enter a room with two doors."
                }
            },
            "state": "default",
            "actionList": [
                {
                    "displayName": "take the left door",
                    "onClickFunction": function () {
                        gotoRoom("torchRoom");
                    },
                    "isOneTime": false
                },
                {
                    "displayName": "take the right door",
                    "onClickFunction": function () {
                        gotoRoom("corridorRoom");
                    },
                    "isOneTime": false
                }
            ]
        },
        {
            "name": "torchRoom",
            "states": {
                "default": {
                    "description": "You enter a small room lit up by torches on the walls."
                }
            },
            "state": "default",
            "actionList": [
                {
                    "displayName": "go back",
                    "onClickFunction": function () {
                        gotoRoom("firstRoom");
                    },
                    "isOneTime": false
                },
                {
                    "displayName": "grab a torch",
                    "displayText": "You grab a torch, and take it off the wall.",
                    "onClickFunction": function () {
                        // TODO implement generateActionBody for AddItemsToInventory
                    },
                    "isOneTime": true
                }
            ]
        },
        {
            "name": "corridorRoom",
            "states": {
                "default": {
                    "description": "You enter a long corridor, with a door at the end and a door on the left side. You also notice a ladder to the left."
                }
            },
            "state": "default",
            "actionList": [
                {
                    "displayName": "go back",
                    "onClickFunction": function () {
                        gotoRoom("firstRoom");
                    },
                    "isOneTime": false
                },
                {
                    "displayName": "take the left door",
                    "onClickFunction": function () {
                        gotoRoom("greenKeyRoom");
                    },
                    "isOneTime": false
                },
                {
                    "displayName": "take the door at the end",
                    "onClickFunction": function () {
                        gotoRoom("chestRoom");
                    },
                    "isOneTime": false
                },
                {
                    "displayName": "take the ladder",
                    "onClickFunction": function () {
                        gotoRoom("exitDoorRoom");
                    },
                    "isOneTime": false
                }
            ]
        },
        {
            "name": "exitDoorRoom",
            "states": {
                "default": {
                    "description": "You enter a room with a single door. The door is locked, and you can see two different keyholes in the door: a red one and a green one."
                },
                "usedOneKey": {
                    "description": "You enter a room with a single door. The door is locked, and you can see two different keyholes in the door: a red one and a green one. You used a key, but the door is still locked."
                    // TODO isActive
                },
                "usedTwoKeys": {
                    "description": "You enter a room with a single door. Now that you used your two keys, the door is unlocked!"
                    // TODO isActive
                }
            },
            "state": "default",
            "actionList": [
                {
                    "displayName": "go back",
                    "onClickFunction": function () {
                        gotoRoom("corridorRoom");
                    },
                    "isOneTime": false
                },
                {
                    "displayName": "open the door",
                    "onClickFunction": function () {
                        gotoRoom("exitRoom");
                    },
                    // TODO is isEnabled
                    "isOneTime": false
                }
            ]
        },
        {
            "name": "chestRoom",
            "states": {
                "default": {
                    "description": "It's an empty room with a small blue chest."
                }
            },
            "state": "default",
            "actionList": [
                {
                    "displayName": "go back",
                    "onClickFunction": function () {
                        gotoRoom("corridorRoom");
                    },
                    "isOneTime": false
                }
            ]
        },
        {
            "name": "greenKeyRoom",
            "states": {
                "default": {
                    "description": "The room is soo dark you cannot see anything."
                },
                "lit": {
                    "description": "You can see inside the dark room with your torch, and you notice a key on the ground."
                    // TODO isActive
                }
            },
            "state": "default",
            "actionList": [
                {
                    "displayName": "go back",
                    "onClickFunction": function () {
                        gotoRoom("corridorRoom");
                    },
                    "isOneTime": false
                },
                {
                    "displayName": "inspect the ground",
                    "displayText": "You grab the green key on the ground.",
                    "onClickFunction": function () {
                        // TODO implement generateActionBody for AddItemsToInventory
                    },
                    // TODO is isVisible
                    "isOneTime": true
                }
            ]
        },
        {
            "name": "exitRoom",
            "states": {
                "default": {
                    "description": "You opened the door with the two keys, and reached the exit of the maze!"
                }
            },
            "state": "default",
            "actionList": [
                {
                    "displayName": "restart",
                    "onClickFunction": function () {
                        // TODO implement generateActionBody for Restart
                    },
                    "isOneTime": false
                }
            ]
        }
    ],
    "items": [
        {
            "name": "blue key",
            "description": "A strange key you found in your pocket.",
            "actionList": [
                {
                    "displayName": "Use",
                    "displayText": "You use the key to open the chest, and you find a red key inside.",
                    "onClickFunction": function () {
                        // TODO implement generateActionBody for AddItemsToInventory
                    },
                    // TODO is isEnabled
                    "isOneTime": false
                }
            ]
        },
        {
            "name": "green key",
            "description": "A strange key you found on the ground.",
            "actionList": [
                
            ]
        },
        {
            "name": "red key",
            "description": "A shiny red key you found in a chest.",
            "actionList": [
                
            ]
        },
        {
            "name": "torch",
            "description": "A torch.",
            "actionList": [
                
            ]
        }
    ],
    "startingState": {
        "room": "firstRoom",
        "items": ["blue key"]
    }
}


class GameState {
    scenarioData;
    currentInventory;
    currentRoom;
    constructor(scenarioData) {
        this.scenarioData = scenarioData;
        this.currentInventory = scenarioData.startingState.items;
        this.currentRoom = scenarioData.startingState.room;
    }
}







/*
utils
*/

function clearSection(container) {
    while (container.childElementCount > 0) {
        container.removeChild(container.lastChild);
    }
}

function deepClone(obj) {
    if (obj === null || typeof obj !== "object") return obj;
    const copy = Array.isArray(obj) ? [] : {};
    for (const key in obj) {
        const value = obj[key];
        copy[key] = deepClone(value);
    }
    return copy;
}

function nameToIndex(name, array) {
    for (var i in array) {
        if (array[i].name === name) {
            return i;
        }
    }
    console.error("name not in array");
}

function printTextCenter(text) {
    var texts = document.getElementById("textContainer");
    while (texts.childElementCount > 0) {
        texts.removeChild(texts.lastChild);
    }

    var textDiv = document.createElement("div");
    textDiv.textContent = text;
    texts.appendChild(textDiv);
}

function printTextObject(text) {
    var objectDescriptionsTextContainer = document.getElementById("objectDescriptionTextContainer");
    clearSection(objectDescriptionsTextContainer);
    var textDiv = document.createElement("div");
    textDiv.textContent = text;
    textDiv.id = "objectText";
    objectDescriptionsTextContainer.appendChild(textDiv);
}






/*
inventory
*/

function createObjectActionButton(action, itemName) {
    var objectButtonContainer = document.getElementById("objectButtonContainer")
    clearSection(objectButtonContainer);
    var newButton = document.createElement("button");
    if (Object.keys(action).includes("isEnabled") && action.isEnabled()) {
        newButton.onclick = function () {
            action.onClickFunction();
            gotoRoom(currentState.currentRoom);
            if (Object.keys(action).includes("displayText")) {
                printTextCenter(action.displayText);
            }
            if (Object.keys(action).includes("isOneTime") && action.isOneTime) {
                removeFromInventory(itemName);
            }
        };
        newButton.className = "button";
    }
    else {
        newButton.className = "greyButton";
        newButton.onclick = function () { };
    }
    newButton.textContent = action.displayName;
    objectButtonContainer.appendChild(newButton);
}

function createInventoryButton(itemName, flash = true) {
    var newButton = document.createElement("button");
    newButton.onclick = function () {
        printTextObject(currentState.scenarioData.items[nameToIndex(itemName, currentState.scenarioData.items)].description);
        for (var i in currentState.scenarioData.items[nameToIndex(itemName, currentState.scenarioData.items)].actionList) {
            createObjectActionButton(currentState.scenarioData.items[nameToIndex(itemName, currentState.scenarioData.items)].actionList[i], itemName)
        }
    };
    newButton.textContent = currentState.scenarioData.items[nameToIndex(itemName, currentState.scenarioData.items)].name;
    newButton.name = itemName;
    newButton.className = "inventoryButton";
    document.getElementById("inventoryContainer").appendChild(newButton);
    currentState.currentInventory.push(currentState.scenarioData.items[nameToIndex(itemName, currentState.scenarioData.items)].name);

    //flash
    if (flash) {
        newButton.className = "yellowInventoryButton"
        setTimeout(() => {
            newButton.className = "inventoryButton";
        }, 1000)
    }
}

function initInventoy() {
    var inventoryButtons = document.getElementById("inventoryContainer")
    //supress all buttons in invetory
    clearSection(inventoryButtons);
    //load items from inventory
    for (var itemId in currentState.currentInventory) {
        createInventoryButton(currentState.scenarioData.startingState.items[itemId], false);
    }
}

function addToInventory(itemName) {
    if (!currentState.currentInventory.includes(itemName)) {
        createInventoryButton(itemName);
    }
}

function removeFromInventory(itemName) {
    inventoryButtons = document.getElementById("inventoryContainer")
    if (currentState.currentInventory.includes(itemName)) {
        index = currentState.currentInventory.indexOf(itemName);
        if (index > -1) {
            currentState.currentInventory.splice(index, 1);
        }
        for (var i in inventoryButtons.childNodes) {
            if (inventoryButtons.childNodes[i].name == itemName) {
                inventoryButtons.removeChild(inventoryButtons.childNodes[i]);
                break;
            }
        }
    }
    else {
        console.log("you don't have the item to remove");
    }
}

function posessItems(itemNames) {
    for (var i in itemNames) {
        if (!currentState.currentInventory.includes(itemNames[i])) {
            return false;
        }
    }
    return true;
}






/*
Rooms
*/

function displayImage(roomName) {
    imageContainer = document.getElementById("imageContainer")
    clearSection(imageContainer);
    newImage = document.createElement("img");
    newImage.src = "images/" + roomName + "_" + currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state + ".png";
    imageContainer.appendChild(newImage);
}

function isCurrentRoom(roomName) {
    return currentState.scenarioData.rooms[nameToIndex(currentState.currentRoom, currentState.scenarioData.rooms)].name === roomName;
}

function isCurrentState(stateName) {
    return currentState.scenarioData.rooms[nameToIndex(currentState.currentRoom, currentState.scenarioData.rooms)].state === stateName;
}

function isCurrentRoomState(stateName) {
    return currentState.scenarioData.rooms[nameToIndex(currentState.currentRoom, currentState.scenarioData.rooms)].state === stateName;
}

function setState(roomName, stateName) {
    currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state = stateName;
}

function updateRoomState(roomName) {
    var foundState = false;
    for (var i in Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states)) {
        stateName = Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states)[i];
        if (stateName !== "default" &&
            Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states[stateName]).includes("isActive") &&
            currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states[stateName].isActive()) {
            currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state = stateName;
            foundState = true;
            break;
        }
    }
    if (!foundState) {
        currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state = "default";
    }

}

function gotoRoom(roomName) {
    currentState.currentRoom = roomName;
    updateRoomState(roomName);
    displayImage(roomName);

    // clear the object description area
    var objectDescriptionsButtonContainer = document.getElementById("objectButtonContainer");
    var objectDescriptionsTextContainer = document.getElementById("objectDescriptionTextContainer");
    clearSection(objectDescriptionsButtonContainer);
    clearSection(objectDescriptionsTextContainer);
    var container = document.createElement("div");
    container.id = "objectButtonContainer";
    objectDescriptionsButtonContainer.appendChild(container);

    // show room description
    var texts = document.getElementById("textContainer");
    clearSection(texts);
    roomDescripion = document.createElement("div");
    if (typeof currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states[currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state].description === "string") {
        roomDescripion.textContent = currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states[currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state].description;
    }
    else if (typeof currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states[currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state].description === "object") {
        if (Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)]).includes("state")) {
            roomDescripion.textContent = currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].states[currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state].description[currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].state];
        }
        else {
            console.error("a room with multiple descriptions should have a state property");
        }
    }
    else {
        console.error("room description should be a string or an object");
    }
    texts.appendChild(roomDescripion);

    // add buttons for actions
    var buttons = document.getElementById("buttonContainer");
    clearSection(buttons);
    for (let actionId in currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList) {
        var newButton = document.createElement("button");
        newButton.textContent = currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].displayName;
        if (!Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId]).includes("isEnabled") ||
            currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].isEnabled()) {
            newButton.className = "button";
            if (Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId]).includes("isOneTime") &&
                currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].isOneTime) {
                if (!Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId]).includes("displayText")) {
                    newButton.onclick = function () {
                        currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].onClickFunction();
                        currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].isVisible = function () { return false; };
                        gotoRoom(roomName);
                    }
                }
                else {
                    newButton.onclick = function () {
                        currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].onClickFunction();
                        currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].isVisible = function () { return false; };
                        gotoRoom(roomName);
                        printTextCenter(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].displayText);
                    }
                }
            }
            else {
                if (!Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId]).includes("displayText")) {
                    newButton.onclick = currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].onClickFunction;
                }
                else {
                    newButton.onclick = function () {
                        currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].onClickFunction;
                        printTextCenter(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].displayText);
                    }
                }
            }
        }
        else {
            newButton.className = "greyButton";
        }
        if (!Object.keys(currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId]).includes("isVisible") ||
            currentState.scenarioData.rooms[nameToIndex(roomName, currentState.scenarioData.rooms)].actionList[actionId].isVisible()) {
            buttons.appendChild(newButton);
        }
    }
}






/*
game loop
*/

var currentState = new GameState(deepClone(data));

function startGame() {
    currentState = new GameState(deepClone(data)); //reset game data
    gotoRoom(currentState.currentRoom);
    initInventoy();
}
  
