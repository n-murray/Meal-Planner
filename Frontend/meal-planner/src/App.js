import logo from './logo.png';
import './App.css';
import React, { Component } from 'react';
import {BrowserRouter, Route, Redirect, Routes} from "react-router-dom";
import Home from "./pages/home/Home";

class App extends Component {

    render() {
        return (
            <div className="App">
              <header className="App-header">
                  <div class="w3-cell-row">
                      <img class="w3-container w3-cell" src={logo} className="App-logo" alt="logo" />
                      <div class="w3-container w3-cell header" >
                          <p>What's for dinner?</p>
                      </div>
                  </div>
              </header>
              <Home/>
            </div>
        )
  }
}

export default App;
