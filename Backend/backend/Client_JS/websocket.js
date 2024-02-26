const stompClient = Stomp.over(new SockJS("/ws-endpoint"));

function connect() {
    const username = document.getElementById("username").value;
    const wsserver = document.getElementById("wsserver").value;

    stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);

        // Subscribe to relevant topics
        stompClient.subscribe("/topic/gameRoom/{roomId}", function (message) {
            console.log("Message from server: " + message.body);

            // Handle messages received from the server
        });

        stompClient.subscribe("/topic/player/{playerName}", function (message) {
            console.log("Message from server: " + message.body);

            // Handle messages received from the server
        });

        // Implement other subscriptions as needed
    });
}

function send() {
    const content = document.getElementById("msg").value;
    stompClient.send("/app/gameRoom/{roomId}", {}, content);
}
