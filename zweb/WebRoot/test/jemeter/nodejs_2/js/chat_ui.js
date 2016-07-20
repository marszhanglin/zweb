function divEscapedContentElement(message) {
	return $("<div></div>").text(message);
}

function divSystemContentElement(message) {
	return $("<div></div>").html('<li>' + message + '</li>');
}

 
function processUserInput(chatApp, socket) {
	var message = $("#message_send_input").val();
	var systemMessage;
	if (message.charAt(0) == '/') {  //ϵͳ����
		systemMessage = chatApp.processCommand(message);
		$("#message_div").append(divSystemContentElement(systemMessage));
	} else {                        //������Ϣ
		chatApp.sendMsgToRoom($("#room_input_id").text(), message);
		$("#message_div").append(divEscapedContentElement(message));
		$("#message_div").scrollTop($("#message_div").prop('scrollHeight'));
	} 
	//�ÿ�
	$('#message_send_input').val('');
}
