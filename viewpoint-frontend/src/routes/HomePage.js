import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const navigate = useNavigate()

  useEffect(() => {
    navigate("/dashboard")
  }, []);
  
  return (
    <div>
      <h1>Добро пожаловать на домашнюю страницу!</h1>
      <p>Здесь вы можете добавить свой контент.</p>
    </div>
  );
}

export default HomePage;