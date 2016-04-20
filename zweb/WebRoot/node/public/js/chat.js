
//����chat����
var Chat = function (socket) {
	this.socket = socket;
}

//����Ϣ��ĳ��
Chat.prototype.sendMsgToRoom = function (room, text) {
	var message = {
		room : room,
		text : text
	};
	this.socket.emit('message', message);
}

//�ı䷿��
Chat.prototype.changeRoom = function (room) {
	this.socket.emit('join', {
		newRoom : room
	});
}

//�ı䷿��
Chat.prototype.changeName = function (name) {
	this.socket.emit('NameChangeAttempts', name);
}

//����
Chat.prototype.processCommand = function (words) {

	var commands = words.split(' ');
	var command = commands[0].substring(1, commands[0].length).toLowerCase();
	console.log(words);
	console.log(commands);
	console.log(command);
	var rtn = false;
	switch (command) {
	case 'join':
	
		this.changeRoom(commands[1]);
		break;
	case 'nike':
		commands.shift();
		this.changeName(commands.join(''));
		break;
	default:
		rtn = 'command not find..';
		console.log('command not find..');
		break;
	}
	return rtn;
}
