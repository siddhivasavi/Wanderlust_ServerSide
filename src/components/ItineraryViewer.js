import React, { Component } from "react";
import '../index.css';
import { Route, Redirect } from "react-router-dom";

export class ItineraryViewer extends Component {

    constructor(props){
        super(props)
        this.state = {
            destination : null,
            highlights:[],
            inclusions: [],
            pace: [],
            bookme: false,
        }
    }

    componentDidMount(){
        //Write logic for fetching data of highlights, packageInclusion and pace from the backend.
        this.fetchDetails();
    }
    book=()=>{
        this.setState({bookme:true});
    }

    render(){
        return (
            <React.Fragment>
                <div className="modal" style={{ display: (this.props.show) ? "block" : "none" , overflow: "scroll"}}>
                    <div className="modal-dialog modal-lg">
                        <div className="modal-content" >
                            <div className= "modal-header">
                                <h4>Itinerary</h4>
                                <span className="close" onClick={this.props.onHide}>&times;</span>
                            </div>
                            <div className="modal-body" >
                                <h5>Tour Spots</h5>
                            
                                <h5>Package Details</h5>
                                
                                <h5> Tour Duration </h5>
                                
                            </div>
                            <div className="modal-footer">
                                <button onClick={this.props.onHide}> Cancel Booking</button>
                                <button>Book</button>
                            </div>
                        </div>
                    </div>
                </div>


            </React.Fragment>

        )
    }


}
export default ItineraryViewer;