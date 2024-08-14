const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/stomp/chats'
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  showChatrooms();
  console.log('Connected: ' + frame);

};

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  $("#create").prop("disabled", !connected);
}

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function createChatroom() {
  // 서버의 api 를 호출
  $.ajax({
    type: 'POST',
    datatype: 'json',
    url: 'chats?title=' + $('chatroom-title').val(),
    success: function (data) {
      console.log('data ', data);
      showChatrooms();
      enterChatroom(data.id, true);
    },
    error: function (request, status, error) {
      console.log('request: ' + request);
      console.log('error: ' + error);
    },
  })
}

function showChatrooms() {
  $.ajax({
    type: 'GET',
    datatype: 'json',
    url: 'chats/',
    success: function (data) {
      console.log('data: ' + data);
      renderChatrooms(data);
    },
    error: function (request, status, error) {
      console.log('request: ' + request);
      console.log("error: " + error);
    },
  })
}

function renderChatrooms(chatrooms) {
  $("#chatroom-list").html("");
  for (let i = 0; i < chatrooms.length; i++) {
    $("#chatroom-list").append(
        "<tr onclick='joinChatroom(" + chatrooms[i].id + ")'><td>"
        + chatrooms[i].id + "</td><td>" + chatrooms[i].title + "</td><td>"
        + chatrooms[i].memberCount + "</td><td>" + chatrooms[i].createdAt
        + "</td></tr>"
    )
  }
}

// 같은 방에 있는 사람들끼리 대화를 주고받을 수 있게 해야한다.
// stomp도 publish, subscribe를 구분해야 한다 (?)

let subscription;

// newMember가 처음 입장이면 입장 안내 메세지 출력
function enterChatroom(chatroomId, newMember) {
  $("#chatroom-id").val(chatroomId);
  $("#messages").html("");
  $("#conversation").show();
  $("#send").prop("disabled", false);
  $("#leave").prop("disabled", false);

  if (subscription != undefined) {
    subscription.unsubscribe();
  }

  subscription = stompClient.subscribe('/sub/chats/' + chatroomId,
      (chatMessage) => {
        showMessage(JSON.parse(chatMessage.body));
      });

  if (newMember) {
    stompClient.publish({
      destination: "/pub/chats/" + chatroomId,
      body: JSON.stringify(
          {'message': " 님이 방에 들어왔습니다."})
    })
  }
}

function joinChatroom(chatroomId) {
  $.ajax({
    type: 'POST',
    datatype: 'json',
    url: '/chats/' + chatroomId,
    success: function (data) {
      console.log("data: " + data);
      enterChatroom(chatroomId, data);
    },
    error: function (request, status, error) {
      console.log('request: ' + request);
      console.log("error: " + error);
    },
  })
}

// 아예 나오는 것
function leaveChatroom() {
  let chatroomId = $("#chatroom-id").val();
  $.ajax({
    type: "DELETE",
    datatype: "json",
    url: "/chats/" + chatroomId,
    success: function (data) {
      console.log("data: " + data);
      showChatrooms();
      exitChatroom(chatroomId);
    },
    error: function (request, status, error) {
      console.log('request: ' + request);
      console.log("error: " + error);
    },
  })
}

function exitChatroom(chatroomId) {
  $("#chatroom-id").val("");
  $("#conversation").hide();
  $("#send").prop("disabled", true);
  $("#leave").prop("disabled", true);
}

function sendMessage() {
  let chatroomId = $("#chatroom-id").val();
  stompClient.publish({
    destination: "/pub/chats" + chatroomId,
    body: JSON.stringify(
        {'message': $("#message").val()})
  });
  $("#message").val("")
}

function showMessage(chatMessage) {
  $("#messages").append(
      "<tr><td>" + chatMessage.sender + " : " + chatMessage.message
      + "</td></tr>");
}

$(function () {
  $("form").on('submit', (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#create").click(() => createChatroom());
  $("#leave").click(() => leaveChatroom());
  $("#send").click(() => sendMessage());
});