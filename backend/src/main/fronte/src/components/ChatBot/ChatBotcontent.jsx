import React, { useState } from 'react';
import './ChatBotcontent.css';

const ChatBotcontent = () => {
  const [formData, setFormData] = useState({
    messages: [],
    currentMessage: ''
  });

  const handleMessageChange = (e) => {
    setFormData({ ...formData, currentMessage: e.target.value });
  };

  const handleSendMessage = () => {
    if (formData.currentMessage.trim() !== '') {
      setFormData({
        messages: [{ text: formData.currentMessage }, ...formData.messages],
        currentMessage: ''
      });
    }
  };

  return (
    <div id="chat-container">
      <div id="chat-messages">
        {formData.messages.map((message, index) => (
          <div key={index} className="message">
            {message.text}
          </div>
        ))}
      </div>
      <div id="user-input">
        <input
          type="text"
          placeholder="메시지를 입력하세요..."
          value={formData.currentMessage}
          onChange={handleMessageChange}
        />
        <button className="send-button" onClick={handleSendMessage}>전송</button>
      </div>
    </div>
  );
};

export default ChatBotcontent;