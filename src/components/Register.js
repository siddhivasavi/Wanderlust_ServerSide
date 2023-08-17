import React from "react";
import { InputText } from "primereact/inputtext";
import { Message } from "primereact/message";
import Axios from "axios";
import { backendUrlRegister } from "../BackendURL";

class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      formValue: {
        userName: "",
        emailId: "",
        password: "",
        confirmPassword: "",
        contactNumber: ""
      },
      formErrorMessage: {
        userName: "",
        emailId: "",
        password: "",
        confirmPassword: "",
        contactNumber: ""
      },
      formValid: {
        userName: false,
        emailId: false,
        password: false,
        confirmPassword: false,
        contactNumber: false,
        buttonActive: false
      },
      successMessage: "",
      errorMessage: ""
    }
  }

  handleChange = (event) => {
    var name = event.target.name
    var value = event.target.value
    var formVal2 = this.state.formValue
    this.setState({ formValue: { ...formVal2, [name]: value } })
    this.validate(name, value)
  }

  validate = (fieldName, value) => {    
     //Implement this function to validate the form fields
     let fieldValidationErrors = this.state.formErrorMessage;
     let formValid = this.state.formValid;
     switch (fieldName) {
      case "userName":
        const pRegex = /[A-Z]([a-z]+){2,}$/
        if (value === "") {
          fieldValidationErrors.userName = "Field required";
          formValid.userName = false;
        } else if (!value.match(pRegex)) {
          fieldValidationErrors.userName = "Please enter a valid name";
          formValid.userName = false;
        } else {
          fieldValidationErrors.userName = "";
          formValid.userName = true;
        }
        break;


      case "emailId":
        const emailRegex = /^.*@.*$/;

        if (value === "") {
          fieldValidationErrors.emailId = "Field required";
          formValid.emailId = false;
        } else if (!value.match(emailRegex)) {
          fieldValidationErrors.emailId = "Please enter a valid email id";
          formValid.emailId = false;
        } else {
          fieldValidationErrors.emailId = "";
          formValid.emailId = true;
        }
        break;


      case "contactNumber":
       
        var contactregx = /^[6-9][0-9]{9}$/;
        if (value === "") {
          fieldValidationErrors.contactNumber = "Field required";
          formValid.contactNo = false;

        } else if (!value.match(contactregx)) {
          fieldValidationErrors.contactNumber = "Enter valid a contact number";
          formValid.contactNumber = false;
        } else {
          fieldValidationErrors.contactNumber = "";
          formValid.contactNumber = true;
        }
        break;

      case "password":
        const pwdRegex = "^(?=.*[0-9])"
          + "(?=.*[a-z])(?=.*[A-Z])"
          + "(?=.*[!@#$%^&+=])"
          + "(?=\\S+$).{7,15}$";

        if (value === "") {
          fieldValidationErrors.password = "Field required";
          formValid.password = false;
        } else if (!value.match(pwdRegex)) {
          fieldValidationErrors.password = "Please enter a valid password";
          formValid.password = false;
        } else {
          fieldValidationErrors.password = "";
          formValid.password = true;
        }
        break;
      

        case "confirmPassword":
          let cf=this.state.formValue.password;
          if(value.trim()!==cf){
            formValid.confirmPassword=false;
            fieldValidationErrors.confirmPassword="Password mismatch"
          }
          else{
            formValid.confirmPassword=true;
            fieldValidationErrors.confirmPassword=""
          }
          break
          default:
          break;
    }

    formValid.buttonActive =
      formValid.userName &&
      formValid.emailId &&
      formValid.contactNumber &&
      formValid.password && 
      formValid.confirmPassword
    this.setState({
      formErrorMessage: fieldValidationErrors,
      formValid: formValid,
      successMessage: "", errorMessage: ""
    });


  }

  handleSubmit = (event) => {
    event.preventDefault()
    this.register()
  }

   
  register = () => {  
    //Implement this function to send the form data to the backend
    this.setState({ successMessage: "", errorMessage: "" });
    Axios.post(backendUrlRegister, this.state.formValue).then(response => this.setState({ successMessage: "Successfully Registered! Welcome to Wanderlust." }))
      .catch(error => this.setState({ errorMessage: "Registration failed! Please run the backend" }))
  }

  render() {
    //Implement this function to create a registration form
    return (
      <div style={{paddingTop:50}}>

        <div className="row my-5 registerSection"> 
        
          <section className="col"></section>
          <section className="col">
          
            
            <form onSubmit={this.handleSubmit} className="form form-horizontal" style={{borderStyle:"ridge", backgroundColor:"azure"}}>
            <div className="card-header text-light bg-secondary" style={{borderRadius:"10px"}}>
              
          <h1><center>REGISTER HERE</center></h1><br /><br />
          </div>
          
            {/* <h1 className="text-info"><center>Register with us!</center></h1><br /><br /> */}
              <div className="form-group">
                <span className="p-float-label">
                  <h6 ><b>User Name</b></h6>
                  <InputText
                    id="userName"
                    required
                    type="text"
                    name="userName"
                    placeholder="Enter user name"
                    onChange={this.handleChange}
                    className="form-control"
                    value={this.state.formValue.userName}
                  />
                  {/* {
                    <label htmlFor="userName" className="font-weight-bolder">
                      User Name
                    </label>
                  } */}
                </span>
                {this.state.formErrorMessage.userName ? (
                  <Message
                    severity="error"
                    text={this.state.formErrorMessage.userName}
                  />
                ) : null}
              </div><br/>


              <div className="form-group">
                <span className="p-float-label">
                  <h6><b>Email ID</b></h6>
                  <InputText
                    id="emailId"
                    required
                    type="email"
                    name="emailId"
                    placeholder="Enter emailID"
                    onChange={this.handleChange}
                    className="form-control"
                    value={this.state.formValue.emailId}
                  />
                  {/* {
                    <label htmlFor="emailId" className="font-weight-bolder">
                      Email
                    </label>
                  } */}
                </span>
                {this.state.formErrorMessage.emailId ? (
                  <Message
                    severity="error"
                    text={this.state.formErrorMessage.emailId}
                  />
                ) : null}
              </div><br/>

              <div className="form-group">
                <span className="p-float-label">
                  <h6 ><b>Contact Number</b></h6>
                  <InputText
                    id="contactNumber"
                    required
                    type="text"
                    min="6000000000"
                    max="9999999999"
                    name="contactNumber"
                    placeholder="Enter contact number"
                    onChange={this.handleChange}
                    className="form-control"
                    value={this.state.formValue.contactNumber}
                  />
                  {/* {
                    <label htmlFor="contactNumber" className="font-weight-bolder">
                      Contact Number
                    </label>
                  } */}
                </span>
                {this.state.formErrorMessage.contactNumber ? (
                  <Message
                    severity="error"
                    text={this.state.formErrorMessage.contactNumber}
                  />
                ) : null}
              </div><br/>


              <div className="form-group">
                <span className="p-float-label">
                  <h6><b>Enter password</b></h6>
                  <InputText
                    id="password"
                    required
                    type="password"
                    name="password"
                    placeholder="Enter password"
                    className="form-control"
                    onChange={this.handleChange}
                    value={this.state.formValue.password}
                  />
                  {/* {
                    <label htmlFor="password" className="font-weight-bolder">
                      Password
                    </label>
                  } */}
                </span>
                {this.state.formErrorMessage.password ? (
                  this.state.formErrorMessage.password.split(",").filter(m => m !== "").map(
                    m => <Message
                      severity="error"
                      text={m}
                    />

                  )
                 ) : null}
              </div><br/>
              <div className="form-group">
                <span className="p-float-label">
                  <h6><b>Confirm password</b></h6>
                  <InputText
                    id="confirmPassword"
                    required
                    type="password"
                    name="confirmPassword"
                    placeholder="Confirm password"
                    className="form-control"
                    onChange={this.handleChange}
                    value={this.state.formValue.confirmPassword}
                  />
                  {/* {
                    <label htmlFor="password" className="font-weight-bolder">
                      Password
                    </label>
                  } */}
               </span>
               {this.state.formErrorMessage.confirmPassword ? (
                   this.state.formErrorMessage.confirmPassword.split(",").filter(m => m !== "").map(
                    m => <Message
                       severity="error"
                       text={m}
                    />

                   )


                 ) : null}
               </div><br/> 
              

              <div className='form-group'><br />
                <button type="submit" className='btn btn-dark' disabled={!this.state.formValid.buttonActive}> Register </button>
              </div>

              <span name="successMessage" className='text-success'>{this.state.successMessage}</span>
              <span name="errorMessage" className='text-danger'>{this.state.errorMessage}</span>
            </form>
          </section>
          <section className="col"></section>
        </div>
      </div>
    )
  }
}
export default Register



{/* <div className="form-group">
                <span className="p-float-label">
                  <h6>Confirm password</h6>
                  <InputText
                    id="password"
                    required
                    type="password"
                    name="password"
                    placeholder="Confirm password"
                    className="form-control"
                    onChange={this.handleChange}
                    value={this.state.formValue.confirmPassword}
                  />
                  {/* {
                    <label htmlFor="password" className="font-weight-bolder">
                      Password
                    </label>
                  } */}
              //   </span>
              //   {this.state.formErrorMessage.confirmPassword ? (
              //     this.state.formErrorMessage.confirmPassword.split(",").filter(m => m !== "").map(
              //       m => <Message
              //         severity="error"
              //         text={m}
              //       />

              //     )


              //   ) : null}
              // </div><br/> */}