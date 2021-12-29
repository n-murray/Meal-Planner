import RestClient from "../../common/utils/rest/RestClient";
import React, { Component } from 'react';
import './randommeal.css';

class RandomMeal extends Component {
    state = {
        list: [
            "$100",
            "$500",
            "$9,999",
            "$1",
            "$60",
            "$1,000",
            "$4.44",
            "$333"
        ],
        radius: 75, // PIXELS
        rotate: 0, // DEGREES
        easeOut: 0, // SECONDS
        angle: 0, // RADIANS
        top: null, // INDEX
        offset: null, // RADIANS
        net: null, // RADIANS
        result: null, // INDEX
        spinning: false
    };

    componentDidMount() {
        // generate canvas wheel on load
        this.renderWheel();
    }

    renderWheel() {
        // determine number/size of sectors that need to created
        let numOptions = this.state.list.length;
        let arcSize = (2 * Math.PI) / numOptions;
        this.setState({
            angle: arcSize
        });

        // get index of starting position of selector
        this.topPosition(numOptions, arcSize);

        // dynamically generate sectors from state list
        let angle = 0;
        for (let i = 0; i < numOptions; i++) {
            let text = this.state.list[i];
            this.renderSector(i + 1, text, angle, arcSize, this.getColor(i));
            angle += arcSize;
        }
    }

    topPosition = (num, angle) => {
        // set starting index and angle offset based on list length
        // works upto 9 options
        let topSpot = null;
        let degreesOff = null;
        if (num === 9) {
            topSpot = 7;
            degreesOff = Math.PI / 2 - angle * 2;
        } else if (num === 8) {
            topSpot = 6;
            degreesOff = 0;
        } else if (num <= 7 && num > 4) {
            topSpot = num - 1;
            degreesOff = Math.PI / 2 - angle;
        } else if (num === 4) {
            topSpot = num - 1;
            degreesOff = 0;
        } else if (num <= 3) {
            topSpot = num;
            degreesOff = Math.PI / 2;
        }

        this.setState({
            top: topSpot - 1,
            offset: degreesOff
        });
    };

    renderSector(index, text, start, arc, color) {
        // create canvas arc for each list element
        let canvas = document.getElementById("wheel");
        let ctx = canvas.getContext("2d");
        let x = canvas.width / 2;
        let y = canvas.height / 2;
        let radius = this.state.radius;
        let startAngle = start;
        let endAngle = start + arc;
        let angle = index * arc;
        let baseSize = radius * 3.33;
        let textRadius = baseSize - 150;

        ctx.beginPath();
        ctx.arc(x, y, radius, startAngle, endAngle, false);
        ctx.lineWidth = radius * 2;
        ctx.strokeStyle = color;

        ctx.font = "17px Arial";
        ctx.fillStyle = "black";
        ctx.stroke();

        ctx.save();
        ctx.translate(
            baseSize + Math.cos(angle - arc / 2) * textRadius,
            baseSize + Math.sin(angle - arc / 2) * textRadius
        );
        ctx.rotate(Math.abs((angle - arc / 2 + Math.PI / 2)*( index % 2 === 0 ? 245 : 45)));
        ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
        ctx.restore();
    }

    getColor(num) {
        // randomly generate rbg values for wheel sectors
        let colList = [
            "DodgerBlue",
            "MediumSeaGreen"
        ]
        return colList[num%2];
    }

    spin = () => {
        // set random spin degree and ease out time
        // set state variables to initiate animation
        let randomSpin = Math.floor(Math.random() * 900) + 500;
        this.setState({
            rotate: randomSpin,
            easeOut: 2,
            spinning: true
        });

        // calcalute result after wheel stops spinning
        setTimeout(() => {
            this.getResult(randomSpin);
        }, 2000);
    };

    getResult = spin => {
        // find net rotation and add to offset angle
        // repeat substraction of inner angle amount from total distance traversed
        // use count as an index to find value of result from state list
        const { angle, top, offset, list } = this.state;
        let netRotation = ((spin % 360) * Math.PI) / 180; // RADIANS
        let travel = netRotation + offset;
        let count = top + 1;
        while (travel > 0) {
            travel = travel - angle;
            count--;
        }
        let result;
        if (count >= 0) {
            result = count;
        } else {
            result = list.length + count;
        }

        // set state variable to display result
        this.setState({
            net: netRotation,
            result: result
        });
    };

    reset = () => {
        // reset wheel and result
        this.setState({
            rotate: 0,
            easeOut: 0,
            result: null,
            spinning: false
        });
    };

    render() {
        let button = this.state.spinning ? (
            <div className="w3-btn meal-button w3-blue" id="reset" onClick={this.reset}>
                reset
            </div>
        ) : (
            <div className="w3-btn meal-button w3-blue" id="spin" onClick={this.spin}>
                spin
            </div>
        )
        return (
            <div className="w3-center wheel">
                <h1>Random Meal Wheel</h1>
                {button}
                <span id="selector">&#9660;</span>
                <canvas
                    id="wheel"
                    width="500"
                    height="500"
                    style={{
                        WebkitTransform: `rotate(${this.state.rotate}deg)`,
                        WebkitTransition: `-webkit-transform ${
                            this.state.easeOut
                        }s ease-out`
                    }}
                />
                <div class="display">
                  <span id="readout">
                    Random Meal:{"  "}<span id="result">{this.state.list[this.state.result]}</span>
                  </span>
                </div>
            </div>
        );
    }
}

export default RandomMeal;