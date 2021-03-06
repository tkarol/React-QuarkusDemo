import React, { useState, useEffect } from 'react';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';

function App() {

  const [data, setData] = useState({ users: [] });
  useEffect(() => {
    const fetchData = async () => {
      const result = await axios(
        'http://localhost:8080/user', { headers: { "Authorization": "Bearer " + localStorage.getItem("react-token") } }
      );
      setData(result.data);
    };
    fetchData();
  }, []);


  return (
    <div className="App">
      <header className="App-header">
        <h1>React App - Protected byXtremeCloud SSO</h1>
        <div>
          <img src={logo} className="App-logo" alt="logo" />
        </div>
        <div>

        <h2>Response from Air Force Material Command's XtremeCloud Tyler BPM REST API: /user </h2>

          <p>Name: {data.name}</p>
          <p>Email:{data.email}</p>

        </div>
      </header>
    </div>
  );
}

export default App;
