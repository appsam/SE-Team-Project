import React, { useState } from 'react';
import './ChatBotcontent.css';
import axios from 'axios';

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
        axios.post('http://localhost:8080/api/gemini/chat', { message: formData.currentMessage })
          .then(response => {
            setFormData(prevFormData => ({
              messages: [...prevFormData.messages, { text: response.data, sender: 'bot' }],
              currentMessage: prevFormData.currentMessage
            }));
          })
          .catch(error => {
            console.error('메시지 전송 오류:', error);
          });

        setFormData({
          messages: [...formData.messages, { text: formData.currentMessage, sender: 'user' }],
          currentMessage: ''
        });
      }
    };

    const handleKeyPress = (e) => {
      if (e.key === 'Enter') {
        handleSendMessage();
      }
    };






  return (
    <div id="chat-container">
      <div id="chat-messages">
        {formData.messages.map((message, index) => (
          <div key={index} className={`message ${message.sender}`}>
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
          onKeyPress={handleKeyPress}
        />
        <button className="send-button" onClick={handleSendMessage}>전송</button>
      </div>
    </div>
  );
};

export default ChatBotcontent;