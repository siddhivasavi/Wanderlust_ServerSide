import { Component } from 'react';
import Axios from 'axios';
import { backendUrlHotDeal, backendUrlPackageSearch } from '../BackendURL';
import { ItineraryViewer } from './ItineraryViewer';
import { Redirect,Route } from 'react-router-dom';
import { Book } from './Book';
import { Link } from 'react-router-dom/cjs/react-router-dom.min';

export class PackageContinent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            destinations: [],
            itineraryButton: false,
            bookButton: false,
            errorMessage: "",
            itinerary: [],
            destForPackage: "",
            destForBook: "",
            bookme: false,
            packages:null,
            id:""
        }
    }
    componentDidMount() {
        //Implement this function to fetch package details by continent from the backend
        this.fetchDetails();
    }

    fetchDetails = () => {
        Axios.get(backendUrlPackageSearch+"/"+this.props.match.params.continent)
            .then(response => {
                this.setState({ destinations: response.data })
            })
            .catch(error => {
                this.setState({ error: "Sorry, we don't operate in this Destination" })
            })
    }
    book=()=>{
        this.setState({bookme:true})
       
        
    }
    card = (d) => {
        var id=d.destinationId;
        let style={
            height:350,
            overflowY:"scroll"
        }
        let details=null;
        if(this.state.packages===d.destinationId){
            details=<div style={style}><emp>{d.details.about}</emp></div>
        }
        return (

            <div className="col-md-3">
                <div className="card card-border" height={300} width={300}>
                    {console.log(d.imageUrl)}
                    <img className="card-img" src={d.imageUrl} alt="Image not Available" height={200} width={200} />
                    <div className="card-header">
                        {d.continent}
                    </div>
                    <div className="card-body">
                        <p className="card-text"><b>Destination Name:</b> {d.destinationName}</p>
                            <p className="card-text"><b>Number of Nights:</b> {d.noOfNights}</p>
                            <p className="card-text"><b>Flight Charges:</b> {d.flightCharge}</p>
                            <p className="card-text"><b>Charges of Person:</b> {d.chargePerPerson}</p>
                            <p className="card-text"><b>Discount:</b> {d.discount}</p>
                            <p className="card-text"><b>Availability:</b> {d.availability}</p>
                            <p className='card-text'><b>Details:</b>
                            {details}</p>
                            <div className='card-footer'>
                            <Link className='btn btn-success'to={"/book/"+id}>Book</Link>  
                            <button className='btn btn-primary' data-toggle='collapse' onClick={(e)=>{
                                e.preventDefault();
                                this.setState({packages:d.destinationId})
                            }}>View</button>
                            </div>
                        </div>
                    </div>
                </div>

        )
    }

    render() {
console.log(this.state.id)
        if(this.state.bookme===true){
            return <Redirect to={"/book/"+this.state.id} component={Book}></Redirect>
        }

        return (

            <>
                <div className='container'>
                    <div className='row'>
                    {this.state.destinations.map(val => this.card(val))}
                    </div>
                </div>
                <span className='text-danger' ><h1><b><center>{this.state.errorMessage}</center></b></h1></span>

                <span className='text-danger' name="error" style={{top:"300px",position:"absolute",left:"250px"}}><h1><b><center>{this.state.error}</center></b></h1></span>
            

            </>
        )
        //Write the logic to display the packages searched by continent

    }
}
    