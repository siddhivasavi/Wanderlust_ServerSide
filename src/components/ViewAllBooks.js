import { Component } from 'react';
import axios from 'axios';
import { backendUrlDeleteBook, backendUrlViewBook } from '../BackendURL';
import { Redirect } from 'react-router-dom';
import { event } from 'jquery';
export class ViewAllBooks extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bookings: [],
            successMessage: "",
            errorMessage: "",
            

            bookings: [],
            userId: sessionStorage.getItem("userId"),
            userName: sessionStorage.getItem("userName"),
            hotDeal: false,
            refundAmout: "",
            bookLogged: true,
            dem: ""
            // bookLogged: true,
            // bookDelete: false,
            // bookingId:0
        }
        // this.res = []
    }

    // delete=(id)=>{
    //     //Implement this function to delete the booking.
    // }

        //Making a GET request to get all the bookings associated with a particular user
        getBookings = () => {
            if (sessionStorage.getItem("userId") == null) {
                return this.setState({ bookLogged: false })
            }
            let url = backendUrlViewBook + sessionStorage.getItem("userId")
            console.log(sessionStorage.getItem("userId"))
            axios.get(url).then(response => {
                this.setState({ bookings: response.data })
            })
                .catch(error => { 
                    if (error.data) {
                        this.setState({ errorMessage: error.response.data.errorMessage });
                        // console.log(error.response.data)
                    } else {
                        this.setState({ errorMessage: "Sorry, You have not planned any trips yet!" })
                    }
                })
        }
        //Making a DELETE request to delete the booking
    cancelBooking =(event)=> {
        axios.delete(backendUrlDeleteBook + event).then(response => {
        }).catch(error => {
        });
        window.location.reload();
    }

    componentDidMount() {
        // if (sessionStorage.getItem("userId") == null) {
        //     return this.setState({ bookLogged: false })
        // }
        // let url = backendUrlViewBook + sessionStorage.getItem("userId")
        // console.log(sessionStorage.getItem("userId"))
        // axios.get(url).then(response => {
        //     this.setState({ bookings: response.data })
        // })
        //     .catch(error => {
        //         if (error.response) {
        //             this.setState({ errorMessage: error.response.data.errorMessage });
        //             console.log(error.response.data)
        //         } else {
        //             this.setState({ errorMessage: "Please run the backend" })
        //         }
        //     })
    
        this.getBookings();
    }
    

    // cardMaker = (book) => {
    //     //Implement the logic to create a card with booking details and cancel booking button
    // }
     //If user has no bookings, offer him to go  to hot deals section
     goToHotDeals = () => {
        this.setState({ hotDeal: true });
    }
    //To get the date in Month Day, Year Format
    getGoodDateFormat = (changeThis) => {
        var date = new Date(changeThis);
        let year = date.getFullYear();
        let day = date.getDate();
        let monthNumber = date.getMonth();
        let months = ["January", "February", "March", "April", "May", "June", "July", "August", "Sptember", "October", "November", "December"];
        let newDate = months[monthNumber] + " " + day.toString() + ", " + year.toString();
        return newDate;
    }
    componentWillUnmount() {
        sessionStorage.removeItem("refundedAmount");
    }

    render() {
        // if (this.state.bookLogged === false) {
        //     alert("Please Login to view your plans")
        //     return <Redirect to={"/"}></Redirect>
        // } if (this.state.errorMessage !== "") {
        //     return <h3>{this.state.errorMessage}</h3>
        // }
        if (this.state.hotDeal === true) {
            return <Redirect to={"/packages"} />
        }
        if(this.state.errorMessage!==""){
            return <h2 className="text-danger" style={{position:"absolute", left:0,top:"300px",height:"auto",width:"100%",bottom:0}} align="center">{this.state.errorMessage}</h2>
        }
        return (
            <div className="container-fluid allBg" style={{ "padding": "100px 10px 10px 10px" }}>
                {sessionStorage.getItem("refundedAmount") ? (<div className="container rounded bg-light  text-center shadow-lg p-3 mb-5 bg-white rounded" style={{ "padding": "20px 20px 20px 20px" }}>
                    <span className="text-success font-weight-bold">Trip successfully canceled, and ${parseInt(sessionStorage.getItem("refundedAmount"), 10).toLocaleString()} as been refunded.</span>
                </div>) : null}
                {this.state.bookings == null ? (<div className="container rounded bg-light  text-center shadow-lg p-3 mb-5 bg-white rounded" style={{ "padding": "20px 20px 20px 20px" }}>
                    <span className="display-4">Sorry!! You don't have any trips planned </span>
                    <br />
                    <br />
                    <button className="btn btn-primary" onClick={this.goToHotDeals}>Click Here to Hot Deals</button>
                </div>) : this.state.bookings.map(booking => (
                    <div key={"B" + booking.bookingId}>
                        <div className="container rounded  shadow-lg p-3 mb-5 bg-white rounded mx-auto" style={{ "padding": "70px 70px 70px 70px" }}>
                            <div className="row">
                                {/* <div className="col-sm">
                                    <img src={booking.destination.details.imageUrl} className="rounded mx-auto d-block" alt="destination" style={{ width: "400px", "margin": "auto", "display": "block" }} />
                                    {console.log(booking.destination.details.imageUrl)}
                                </div> */}
                                <div className="col-lg-5" style={{ "width": "500px" }}>
                                    <h3 className="font-weight-bold">{booking.destination.destinationName}</h3>
                                    <br />
                                    <div className="row">
                                        <div className="col-sm-4"><span className="font-weight-bold text-secondary">From: </span></div>
                                        <div className="col-md  font-weight-bold"><em>{this.getGoodDateFormat(booking.checkIn)}</em></div>
                                    </div>
                                    <div className="row">
                                        <div className="col-sm-4"><span className="font-weight-bold text-secondary">To: </span></div>
                                        <div className="col-sm font-weight-bold"><em>{this.getGoodDateFormat(booking.checkOut)}</em></div>
                                    </div>
                                    <div className="row">
                                        <div className="col-sm-4"><span className="font-weight-bold text-secondary">No of People: </span></div>
                                        <div className="col-sm font-weight-bold"><em>{booking.noOfPeople}</em></div>
                                    </div>
                                </div>
                                <div className="col-sm">
                                    <h2 className="text-center font-weight-bold">You Paid: <span className="text-success">${(booking.totalCost).toLocaleString()}</span></h2>
                                    <br /><br />
                                    <button type="button" className="btn btn-danger btn-block" data-toggle="modal" data-target={"#B" + booking.bookingId}>Claim Refund</button>
                                    <div className="modal fade" id={"B" + booking.bookingId} tabIndex="-1" aria-labelledby={"B" + booking.bookingId + "Label"} aria-hidden="true">
                                        <div className="modal-dialog modal-lg modal-dialog-centered">
                                            <div className="modal-content">
                                                <div className="modal-header">
                                                    <h5 className="modal-title" id={"B" + booking.bookingId + "Label"}>Claim refund?</h5>
                                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true"></span>
                                                    </button>
                                                </div>
                                                <div className="modal-body">
                                                    <p>Are you sure you want to cancel your trip to "<em>{booking.destination.destinationName}</em>" and claim refund?</p>
                                                </div>
                                                <div className="modal-footer">
                                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                                                    <button type="button" className="btn btn-danger" data-dismiss="modal" onClick={()=>{this.cancelBooking(booking.bookingId)}} value={booking.bookingId}>Cancel Trip</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <span>{this.state.errorMessage}</span>
                        </div>
                    </div>
                ))}
            </div>
        );
    }
    }
    export default ViewAllBooks;
    //Write the remaining logic
