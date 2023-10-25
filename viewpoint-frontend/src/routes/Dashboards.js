import React from 'react';
import { Link } from 'react-router-dom';

function Dashboards() {
  return (
    <div>
      <h2>Компонент 1</h2>
      <Link to="/">На домашнюю страницу</Link>
    </div>
  );
}

export default Dashboards;