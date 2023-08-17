import React, { Component } from 'react'
import axios from "axios";
import { backendUrlHotDeal } from "../BackendURL";
import { Redirect } from "react-router-dom";
import ViewAllPackages from './ViewAllPackages';
import { Link } from 'react-router-dom/cjs/react-router-dom.min';



export default class HotDeals extends Component {

    constructor(props) {
        super(props)

        this.state = {
            data: [],
            viewAllPackages: false
        }
    }


    componentDidMount(){
        //Write logic for fetching data of highlights, packageInclusion and pace from the backend.
        this.getData();
    }

    getData = (event) => {
        // event.preventDefault();

        axios.get(backendUrlHotDeal).then(response => {
            this.setState({ 
                data: response.data,
             })
        }).catch(error => this.setState({
            errorMsg: "Please run the Backend"
        }));
    }

    viewAllPackages=()=>{
        this.setState({destinationId:this.state.data})
        this.setState({
            viewAllPackages:true,
        })
    }


    render() {
          if (this.state.viewAllPackages === true)
                    return  <Redirect to={"/viewAllPackages/:destinationId"} component={ViewAllPackages} />;
        return (
            <>
                {/* {this.getData} */}

                {/* <button className='btn btn-success' type='submit' onClick={this.getData}>Get Data</button><br /> */}
              

                <div className='container' style={{paddingTop:70}}>
                    <div className='row'>
                        {this.state.data.map((cardData) => {
                            const { destinationId, continent, destinationName, imageUrl } = cardData;
                            return (
                                <div className='col-md-4'>
                                <div key={destinationId} className="card" height={400} width={512}>
                                    <img className="card-img-top" src={imageUrl} alt="Card image" height={200} width={200} />
                                    <div class="card-body">
                                        <h4 class="mdl-card__title">{continent}</h4>
                                        <p>{destinationName}</p>
                                        {/* <a href="#" class="btn btn-primary">View Deal</a> */}
                                        <Link className='btn btn-primary'  to={"/viewAllPackages/"+destinationId}>View Deals</Link>
                                    </div>
                                </div>
                                </div>
                            );
                        })}
                    </div>
                </div>



            </>
        )
    }
}

    