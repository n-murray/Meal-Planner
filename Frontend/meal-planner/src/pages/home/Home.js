import WeekPlan from "../../widgets/weekplan/WeekPlan";
import React, { Component } from 'react';
import './home.css';
import RandomMeal from "../../widgets/randommeal/RandomMeal";

class Home extends Component {

    state = {
        weekPlan: false,
        random: false
    }

    weekPlanClick() {
        this.setState({
            weekPlan: true,
            random: false
        });
    }

    randomClick() {
        this.setState({
            weekPlan: false,
            random: true
        });
    }

    render() {

        let weekPlan = this.state.weekPlan ? <WeekPlan/> : <div className="w3-btn w3-container w3-card w3-blue w3-hover-cyan home-button" onClick={() => {this.weekPlanClick()}}>Plan of the week.</div>
        let random = this.state.random ? <RandomMeal/> : <div className="w3-btn w3-container w3-card w3-blue w3-hover-cyan home-button" onClick={() => {this.randomClick()}}>Random Meal</div>
        return (
            <div className="w3-center w3-container home">
                {weekPlan}
                {random}
            </div>
        )
    }
}

export default Home;