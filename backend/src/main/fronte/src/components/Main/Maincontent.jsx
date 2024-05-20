import React, { useState, useEffect } from 'react';
import './Maincontent.css';

const Maincontent = () => {
  const [text, setText] = useState('');

  useEffect(() => {
    fetch('https://baconipsum.com/api/?type=meat-and-filler')
      .then(response => response.json())
      .then(data => setText(data[0]));
  }, []);

  return (
    <div>
      <h1>안녕하세요</h1>
    </div>
  );
};

export default Maincontent;
