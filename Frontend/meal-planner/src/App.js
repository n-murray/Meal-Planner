import logo from './logo.png';
import './App.css';
import React, { Component } from 'react';
import { BrowserRouter, Route, Redirect } from "react-router-dom";

class App extends Component {
  render() {
    return (
        <BrowserRouter>
            <div className="App">
              <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                  Meal Planner Client
                </p>
                <a
                  className="App-link"
                  href="https://reactjs.org"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Learn React
                </a>
              </header>
            </div>
        </BrowserRouter>
    )
  }
}

export default App;
