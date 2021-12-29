import RestClient from "../../common/utils/rest/RestClient";
import React, { Component } from 'react';
import './weekplan.css';

class WeekPlan extends Component {

    state = {
        plan: null
    }

    async componentDidMount() {
        let plan = this.state.plan;
        if (plan == null) {
            let restClient = new RestClient();
            let date = new Date()
            let lastMonday = date.getDate() - date.getDay()+1;
            let dateString = new Date(date.setDate(lastMonday)).toISOString().split('T')
            plan = await restClient.getWeekPlan(dateString[0])
            this.setState({
                plan: plan
            });
        }
    }

    render() {
        let plan = this.state.plan;
        let list = ( plan != null && plan !== RestClient.NO_CONTACT )?
            <div class="w3-container w3-card-4 w3-white plan-table">
                <div class="w3-container w3-cell-row w3-card-4 plan-row  w3-blue">
                    <div class="plan-day w3-container w3-cell w3-left">Monday</div>
                    <div class="plan-meal w3-container w3-cell w3-center"> {plan.meals['MONDAY']}</div>
                </div>
                <div class="w3-container w3-cell-row plan-row">
                    <div class="plan-day w3-container w3-cell w3-left">Tuesday</div>
                    <div class="plan-meal w3-container w3-cell  w3-center"> {plan.meals['TUESDAY']}</div>
                </div>
                <div class="w3-container w3-cell-row w3-card-4 plan-row w3-blue">
                    <div class="plan-day w3-container w3-cell w3-left">Wednesday</div>
                    <div class="plan-meal w3-container w3-cell w3-center"> {plan.meals['WEDNESDAY']}</div>
                </div>
                <div class="w3-container w3-cell-row plan-row">
                    <div class="plan-day w3-container w3-cell w3-left">Thursday</div>
                    <div class="plan-meal w3-container w3-cell w3-center"> {plan.meals['THURSDAY']}</div>
                </div>
                <div class="w3-container w3-cell-row w3-card-4 plan-row w3-blue">
                    <div class="plan-day w3-container w3-cell w3-left">Friday</div>
                    <div class="plan-meal w3-container w3-cell w3-center"> {plan.meals['FRIDAY']}</div>
                </div>
                <div class="w3-container w3-cell-row plan-row">
                    <div class="plan-day w3-container w3-cell w3-left">Saturday</div>
                    <div class="plan-meal w3-container w3-cell w3-center"> {plan.meals['SATURDAY']}</div>
                </div>
                <div class="w3-container w3-cell-row w3-card-4 plan-row  w3-blue">
                    <div class="plan-day w3-container w3-cell w3-left">Sunday</div>
                    <div class="plan-meal w3-container w3-cell  w3-center"> {plan.meals['SUNDAY']}</div>
                </div>
            </div>
            :
            <div>
                No plan to view
            </div>
        return (
            <div>
                {list}
            </div>
        )
    }
}

export default WeekPlan;