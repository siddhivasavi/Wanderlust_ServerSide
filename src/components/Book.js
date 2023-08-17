import { Component } from 'react';
import axios from 'axios';
import { backendUrlItinerary, backendUrlBook } from '../BackendURL';
import "../custom.css";
import { Redirect } from 'react-router-dom';

export class Book extends Component {

    constructor(props) {
        super(props);
        this.state = {
            booking: [],
            destination: [],
            highlights: [],
            details: [],
            itinerary: [],
            formValue: {
                noOfPeople: "",
                checkIn: "",
                totalCost: "",
                user: {
                    "userId": sessionStorage.getItem("userId"),
                    "userName": sessionStorage.getItem("userName")

                }
            },
            id:"",
            formErrorMessages: {
                noOfPeople: "",
                checkIn: ""
            },
            formValid: {
                noOfPeople: false,
                checkIn: false,
                buttonActive: false
            },
            errorMessage: "",
            successMessage: "",
            totalCost: "",
            tripEnd: "",
            displayDate: "",
            flightCharge: 0,
            back: false,
            accords: {
                accordButton1: false,
                accordButton2: false,
                accordButton3: false
            }
        }
        console.log(sessionStorage);
       
    }

    componentDidMount() {

       //Write logic for fetching itinerary details from the backend.
       this.fetchDetails();
    }
    fetchDetails = () => {
        axios.get(backendUrlItinerary+"/"+ this.props.match.params.id)
            .then(response => {
                this.setState({ destination: response.data })
                this.setState({ details: this.state.destination.details })
                this.setState({ highlights: this.state.destination.details.highlights })
                this.setState({ itinerary: this.state.destination.details.itinerary })
                this.setState({ tripEnd: this.state.destination.noOfNights })
                this.setState({ id: sessionStorage.getItem("userId") })
                console.log(backendUrlBook + this.state.id + "/" + this.props.match.params.id)
            })
            .catch(error => {
                this.setState({ error: "Sorry, we don't operate in this Destination" })
            })
    }
    setTripEnd = (date) => {
        var d = new Date(date);
        let d1 = new Date(d);
        d1.setDate(d1.getDate() + this.state.tripEnd)
        this.setState({ displayDate: String(d1.getFullYear() + "/" + (d1.getMonth() + 1) + "/" + d1.getDate()) })
      //Implement this function to calculate the trip end date with the help of No. of nights

    }

    totalAmount = (name, value) => {
        var coste = this.state.destination.chargePerPerson;
        console.log(this.state.destination.chargePerPerson+"abc")
        if (name == "noOfPeople") {
            console.log(coste);
            this.setState({ totalCost: (coste * value) });
            console.log(this.state.totalCost);
        }
       //Implement this function the total amount for the booking
    }

    handleChange = (event) => {
        var name = event.target.name;
        var value = event.target.value;
        var formVal = this.state.formValue;
        this.setState({ formValue: { ...formVal, [name]: value } })
        this.validateField(name, value)
        this.totalAmount(name, value)
    }

    validateField = (fieldName, value) => {
        let formErrorMessages = this.state.formErrorMessages;
        let formValid = this.state.formValid;
        switch (fieldName) {
            case "noOfPeople":
                if (value === "") {
                    formErrorMessages.noOfPeople = "Field Required";
                    formValid.noOfPeople = false
                }
                else {
                    if ((value < 1) || (value > 5)) {
                        formErrorMessages.noOfPeople = "Number of travelers should be between 1 and 5";
                        formValid.noOfPeople = false
                    }
                    else {
                        formErrorMessages.noOfPeople = "";
                        formValid.noOfPeople = true
                    }
                }
                break;
                case "checkIn":
                    if (value === "") {
                        formErrorMessages.checkIn = "Field Required";
                        formValid.checkIn = false
                    }
                    else {
                        let today = new Date()
                        let dateOfJourney = new Date(value)
    
    
                        if (dateOfJourney <= today) {
    
                            formErrorMessages.checkIn = "Date of Journey should be after todays";
                            formValid.checkIn = false
                        }
                        else {
                            formErrorMessages.checkIn = "";
                            formValid.checkIn = true
                            this.setTripEnd(value);
                        }
                    }
                    break;
            }
    
     //Implement this function to validate the booking fields
        
    }

    handleSubmit = (event) => {
        event.preventDefault()
        this.submitBooking();

    }
    submitBooking = () => {
        let start = new Date(this.state.displayDate)
        let month = start.getMonth() + 1;
        let date = start.getDate() + 1;
        if (month < 10) {
            month = '0' + month;
        }
        if (date < 10) {
            date = '0' + date;
        }
        let date1 = start.getFullYear() + '-' + month + '-' + date


        let end = new Date(this.state.formValue.checkIn)
        let endMonth = end.getMonth() + 1;
        let endDate = end.getDate() + 1;
        if (endMonth < 10) {
            endMonth = '0' + endMonth;
        }
        if (endDate < 10) {
            endDate = '0' + endDate;
        }
        let date2 = end.getFullYear() + '-' + endMonth + '-' + endDate
          //    console.log(date1);
        //    console.log(this.state.formValue.checkIn)

        var formJSON = {
            checkIn: date2,
            checkOut: date1,
            totalCost:this.state.totalCost,
            noOfPeople: this.state.formValue.noOfPeople,
           // totalCost:this.state.totalCost
        };

        this.setState({ successMessage: "", errorMessage: "" });
        //    let val=JSON.stringify(formJSON)
        axios.post(backendUrlBook + this.state.id + "/" + this.props.match.params.id, formJSON)
            .then(response => this.setState(
                { successMessage: " Booking Done with booking ID: " + response.data }))
            .catch(error => this.setState(
                { errorMessage: "Please run the backend" }))
       //IAdd the logic to submit the booking to backend
    }

    

    closeAccord = (event) => {
        Object.keys(this.state.accords).map(k => {
            if (this.state.accords[k] === true) {
                let accordKey = this.state.accords;
                accordKey[k] = false;
                this.setState({ accords: accordKey })
            } else if (event.target.id === k) {
                let accordKey = this.state.accords;
                accordKey[k] = true;
                this.setState({ accords: accordKey })
            } else {
                let accordKey = this.state.accords;
                accordKey[k] = false;
                this.setState({ accords: accordKey })
            }
        })
    }
    goBack=()=>{
        this.setState({goBack:true})
    }
    render() {
        //Add the missing logic to complete the view
        if (this.state.goBack === true) {
            return <Redirect to="/"></Redirect>
        }

        if (sessionStorage.getItem("userId") === null) {
            return <Redirect to="/login" />
        }
        return (
            <>
                <span className="text-success font-weight-bold">{this.state.successMessage}</span>
                <div className="row px- 2 py-3">
                    <div className="col-lg-6 col-md-5 col-sm-12">
                        <h3>{this.state.destination.destinationName}</h3>
                        <div className="card">
                            <div className="card-header">
                                <div className="d-flex justify-content-between">
                                    <span id="accord">Overview</span>
                                    <span id="accordButton1" className="close" style={{ transform: this.state.accords.accordButton1 ? "rotate(135deg)" : "rotate(90deg)" }} onClick={this.closeAccord}>+</span>
                                </div>
                            </div>
                        </div>
                        <div className="hiddenBox" style={{ display: this.state.accords.accordButton1 ? "block" : "none" }}>
                            <p>{this.state.details.about}</p>
                        </div>
                        <div className="card">
                            <div className="card-header">
                                <div className="d-flex justify-content-between">
                                    <span id="accord">Highlights</span>
                                    <span id="accordButton2" className="close" style={{ transform: this.state.accords.accordButton2 ? "rotate(135deg)" : "rotate(90deg)" }} onClick={this.closeAccord}>+</span>
                                </div>
                            </div>
                        </div>
                        <div className="hiddenBox" style={{ display: this.state.accords.accordButton2 ? "block" : "none" }}>
                            <p>{this.state.highlights.toString().split(",").map(h => <li key={h}>{h}</li>)}</p>
                        </div>
                        <div className="card">
                            <div className="card-header">
                                <div className="d-flex justify-content-between">
                                    <span id="accord">Tour Pace</span>
                                    <span id="accordButton3" className="close" style={{ transform: this.state.accords.accordButton3 ? "rotate(135deg)" : "rotate(90deg)" }} onClick={this.closeAccord}>+</span>
                                </div>
                            </div>
                        </div>
                        <div className="hiddenBox" style={{ display: this.state.accords.accordButton3 ? "block" : "none" }}>
                            <span>1: {this.state.itinerary.firstDay}</span><br /><br />
                            <span>2: {this.state.itinerary.restOfDays}</span><br /><br />
                            <span>3: {this.state.itinerary.lastDay}</span><br /><br />
                        </div>
                    </div>
                    <div className="col-lg-6 col-md-7 col-sm-12">
                        <div className="card">
                            <div className="card-header">
                                <h4 className="text-center font-weight-bold text warning">Book Your Trip</h4>
                            </div>
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label className="font-weight-bold d-flex justify-content-start">Number of Travellers</label>
                                        <input name="noOfPeople" className='form-control' min='1' type='number' onChange={this.handleChange}></input>
                                    </div>
                                    <span name="noOfPeopleError" className='text-danger'>{this.state.formErrorMessages.noOfPeople}</span>
                                    <div className="form-group">
                                        <label className="font-weight-bold d-flex justify-content-start">Trip Start Date</label>
                                        <input name="checkIn" className='form-control' type='date' onChange={this.handleChange}></input>
                                    </div>
                                    <span name="checkInError" className='text-danger'>{this.state.formErrorMessages.checkIn}</span>
                                </form>
                                <span className="font-weight-bold">Your Trip Ends on: {this.state.displayDate}</span><br /><br />
                                <h5>You Pay: ${this.state.totalCost}</h5>
                            </div>
                            <div className="card-footer d-flex justify-content-between">
                                <button type="submit" className='btn-success' name="book" onClick={this.handleSubmit} >CONFIRM BOOKING</button>
                                <button name="goBack"  className='btn-secondary'  onClick={this.handleSubmit}>GO BACK</button>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        )
    }


}
