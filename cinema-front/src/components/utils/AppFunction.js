import axios from "axios";
import moment from "moment";

export const api = axios.create({
   // baseURL:"http://localhost:8080"
   baseURL: import.meta.env.VITE_API_BASE_URL,
    withCredentials: true
});

/*kada se korisnik loguje, setuje se token i kredencijali*/
/*export const getHeader = () =>{
    const token = localStorage.getItem("token");
    return {
		Authorization: `Bearer ${token}`,
		"Content-Type": "application/json"
	}
}*/
export const getHeader = () => {
    const token = localStorage.getItem("token");
    console.log("Token iz localStorage:", token);
    if (!token) {
        alert("Niste ulogovani. Molimo vas da se prijavite!");
        return {};
    }
    return {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
    };
};

export const getHeaderForFormData = () => {
    const token = localStorage.getItem("token");
    return {
        Authorization: `Bearer ${token}`
        // No Content-Type for FormData
    }
}

export async function addMovie(name, duration, distributor, country, year, description, photo, token) {
    const formData = new FormData();
    formData.append("name", name);
    formData.append("duration", duration);
    formData.append("distributor", distributor);
    formData.append("country", country);
    formData.append("year", year);
    formData.append("description", description);
    formData.append("photo", photo);

    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/movies/add/new-movie`, {
        method: 'POST',
        body: formData,
        headers: {
            'Authorization': 'Bearer ' + token,
        },
    });

    if (response.ok) {
        const data = await response.json();
        console.log(data);
        return true;
    } else {
        const error = await response.json();
        console.error('Error:', error);
        return false;
    }
}

/*export async function addMovie(name, duration,distributor,country,year,description, photo){
    const formData = new FormData();
    formData.append("name",name);
    formData.append("duration",duration);
    formData.append("distributor",distributor);
    formData.append("country",country);
    formData.append("year",year);
    formData.append("description",description);
    
    const fileInput = document.querySelector('#photo');
    const file = fileInput.files[0];
    const reader = new FileReader();
    reader.readAsArrayBuffer(file);
    reader.onloadend = function() {
    const blob = new Blob([reader.result], { type: file.type });
    formData.append('photo', blob);

    fetch('http://localhost:8080/movies/add/new-movie', {
        method: 'POST',
        body: formData,
        headers: {
            'Authorization': 'Bearer ' + token,
        },
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
    };
    const response = await api.post(`/movies/add/new-movie`,formData,{
        headers: getHeader()
    });
    if(response.status === 201){
        return true;
    }
    else{
        return false;
    }
}*/

export async function updateMovie(movieId, movieData) {
    const formData = new FormData();
    formData.append("name", movieData.name);
    formData.append("duration", movieData.duration);
    formData.append("distributor", movieData.distributor);
    formData.append("country", movieData.country);
    formData.append("year", movieData.year);
    formData.append("description", movieData.description);
    
    if (movieData.photo) {
        formData.append("photo", movieData.photo);
    }

    const headers = getHeaderForFormData();

    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/movies/update/${movieId}`, {
        method: 'PUT',
        body: formData,
        headers: headers,
    });

    if (!response.ok) {
        throw new Error('Failed to update movie');
    }

    return response.json(); 
}

/*export async function updateMovie(movieId, movieData){
    const formData = new FormData();
    formData.append("name",movieData.name);
    formData.append("duration",movieData.duration);
    formData.append("distributor",movieData.distributor);
    formData.append("country",movieData.country);
    formData.append("year",movieData.year);
    formData.append("description",movieData.description);
    formData.append("photo",movieData.photo);
    const response = await api.put(`/movies/update/${movieId}`, movieData,{
        headers : getHeader()
    });
    return response;
}*/

export async function getAllMovies(){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/movies/all-movies`);
        return result.data;
    }
    catch(error){
        //console.error("Error fetching movies:", error);
		throw new Error(`Error fetching movies : ${error.message}`)
    }
}

export async function getMovieById(movieId){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/movies/movie/${movieId}`);
        return result.data;
    }
    catch(error){
        throw new Error(`Error fetching movie ${error.message}`);
    }
}

export async function deleteMovie(movieId){
    try{
        const response = await api.delete(`${import.meta.env.VITE_API_BASE_URL}/movies/delete/movie/${movieId}`,{
            headers: getHeader()
        })
        return response.data;
    }
    catch(error){
        throw new Error(`Error deleting movie ${error.message}`);
    }
}

export async function addComingSoonMovie(name, duration, distributor, country, year, description,releaseDate, photo, token) {
    const formData = new FormData();
    formData.append("name", name);
    formData.append("duration", duration);
    formData.append("distributor", distributor);
    formData.append("country", country);
    formData.append("year", year);
    formData.append("description", description);
    formData.append("releaseDate",releaseDate);
    formData.append("photo", photo);

    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/movieComingSoon/add/new-movie-coming-soon`, {
        method: 'POST',
        body: formData,
        headers: {
            'Authorization': 'Bearer ' + token,
        },
    });

    if (response.ok) {
        const data = await response.json();
        console.log(data);
        return true;
    } else {
        const error = await response.json();
        console.error('Error:', error);
        return false;
    }
}

export async function deleteMovieComingSoon(movieComingSoonId){
    try{
        const response = await api.delete(`${import.meta.env.VITE_API_BASE_URL}/movieComingSoon/delete/comingSoon/${movieComingSoonId}`,{
            headers: getHeader()
        })
        return response.data;
    }
    catch(error){
        throw new Error(`Error deleting movie ${error.message}`);
    }
}

export async function allUpcomingMovies(){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/movieComingSoon/all-upcoming-movies`);
        return result.data;
    }
    catch(error){
        console.error("Error fetching movies:", error);
		throw new Error("Error fetching movies")
    }
}

export async function getUpcomingMovies(){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/movieComingSoon/find-upcoming-movies`);
        return result.data;
    }
    catch(error){
        console.error("Error fetching movies:", error);
		throw new Error("Error fetching movies")
    }
}

export async function getMovieDetails(movieComingSoonId){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/movieComingSoon/movie/${movieComingSoonId}`);
        return result.data;
    }
    catch (error) {
        //console.error("Error fetching movie details:", error);
        throw new Error(`Error fetching movie details: ${error.message}`);
    }
}

export async function addTicket(projection, seatNumber, price, status){
    const formData = new FormData();
    formData.append("projection",projection)
    formData.append("seatNumber",seatNumber)
    formData.append("price",price)
    formData.append("status",status);
    const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/tickets/add/new-ticket`,formData,{
        headers:getHeader()
    });
    if(response.status === 201){
        return true;
    }
    else{
        return false;
    }
}

export async function updateTicketStatus(ticketId, statusData){
    const formData = new FormData();
    formData.append("ticketId",statusData.ticketId);
    formData.append("status",statusData.status);
    const response = await api.patch(`${import.meta.env.VITE_API_BASE_URL}/tickets/${ticketId}/status`,statusData,{
        headers:getHeader()
    });
    return response;
}

export async function getTicketsByStatus(status){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/status/${status}`);
        return response.data;
    }
    catch(error){
        throw new Error(`Error fetching tickets by status : ${error.message}`);
    }
}

export async function cancelPurchasedTicket(ticketId){
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/tickets/cancel-purchased/${ticketId}`);
        return response.data;
    }
    catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data);
        }
        else{
            throw new Error(`Error cancelling purchased ticket : ${error.message}`);
        }
    }
}

export async function cancelReservedTicket(ticketId){
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/tickets/cancel-reserved/${ticketId}`)
        return response.data;
    }
    catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data);
        }
        else{
            throw new Error(`Error cancelling reserved ticket : ${error.message}`);
        }
    }
}

export async function purchaseReservedTicket(ticketId){
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/tickets/purchase-reserved/${ticketId}`)
        return response.data;
    }
    catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data);
        }
        else{
            throw new Error(`Error buying ticket : ${error.message}`);
        }
    }
}

export async function countByTicketsProjectionId(projectionId){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/count/${projectionId}`);
        return result.data;
    }
    catch(error){
        throw new Error(`Error counting tickets : ${error.message}`)
    }
}

export async function getTicketsByUserEmail(email){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/user/${email}/bought-tickets`);
        return response.data;
    }
    catch(error){
        throw new Error(`Error fetching tickets by user email : ${error.message}`);
    }
}

export async function findByTicketConfirmationCode(confirmationCode){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/confirmation/${confirmationCode}`);
        return response.data;
    }
    catch(error){
        throw new Error(`Error fetching tickets : ${error.message}`);
    }
}

export async function getTicketById(ticketId){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/ticket/${ticketId}`);
        return response.data;
    }
    catch(error){
        throw new Error(`Error fetching ticket : ${error.message}`);
    } 
}

export async function getTakenSeats(projectionId) {
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/taken/${projectionId}`,{
            headers:getHeader()
        })
        return response.data;
    }
    catch(error){
        throw new Error(`Error finding available seats ${error.message}`);
    }
}

/*export async function buyTicket(projectionId, ticketRequest, status){
    try{
        const response = await api.post(`/tickets/ticket/${projectionId}/buy`,ticketRequest,
            {params : {status}}
        );
        return response.data;
    }
    catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data);
        }
        else{
            throw new Error(`Error buying ticket : ${error.message}`);
        }
    }
}*/

/*export async function buyTicket(projectionId, ticketRequest, status) {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Niste ulogovani. Molimo vas da se prijavite!");
        return;
    }
    if (!status || status === "undefined") {
        alert("Molimo izaberite status karte.");
        return;
    }
    if (!ticketRequest || !ticketRequest.seatNumber || !ticketRequest.userId) {
        alert("Nedostaju podaci o karti.");
        return;
    }
    const headers = {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
    };
    console.log("Status koji šaljemo:", status);
    console.log("TicketRequest objekat:", ticketRequest);
    try {
        const response = await api.post(
            `/tickets/ticket/${projectionId}/buy?status=${status}`,
            ticketRequest,
            { headers }
        );
        return response.data;
    } catch (error) {
        console.error("Greška:", error);

        const errorMsg =
            error.response?.data?.message || error.response?.data || error.message;

        alert("Error purchasing ticket: " + errorMsg);
        throw new Error(errorMsg);
    }
}*/

export async function buyTicket(projectionId, ticketRequest, status) {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Niste ulogovani.');
      return;
    }
    if (!status) {
      alert('Izaberite status!');
      return;
    }
    const headers = {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    };
  
    try {
      const res = await api.post(
        `${import.meta.env.VITE_API_BASE_URL}/tickets/ticket/${projectionId}/buy?status=${status}`,
        ticketRequest,
        { headers }
      );
  
      console.log('Kupovina uspešna!', res.data);
      return res.data; // ovo vraća string sa confirmation code-om
    } catch (err) {
      if (err.response && err.response.data) {
        console.error('Greška od servera:', err.response.data);
      } else {
        console.error('Nešto drugo nije u redu:', err.message);
      }
      throw err; // opcionalno
    }
  }

export async function getAllTickets() {
    try {
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/all-tickets`, {
            headers: getHeader()
        });
        return response.data;
    } catch (error) {
        console.error("Error fetching tickets:", error);
        throw new Error("Error fetching tickets");
    }
}

export async function getAvailableTickets(dates, projection, status){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/available-tickets`,{
            params:{
                dates:dates.trim(),
                projection:projection.trim(),
                status:status.trim()
            }
        });
        return response;
    }
    catch (error) {
        throw new Error("Error fetching available tickets");
    }
}

export async function deleteTicket(ticketId) {
    try{
        const response = await api.delete(`${import.meta.env.VITE_API_BASE_URL}/tickets/delete/ticket/${ticketId}`,
            {headers:getHeader()}
        )
        return response.data;
    }
    catch(error){
        throw new Error(`Error deleting ticket ${error.message}`);
    }
}

export async function addProjection(movieId, projectionType, hallId, dateTime, price, photo) {
    const formData = new FormData();
    formData.append("movieId", movieId); 
    formData.append("projectionType", projectionType); 
    formData.append("hallId", hallId); 
    formData.append("dateTime", moment(dateTime).format('YYYY-MM-DDTHH:mm:ss')); 
    formData.append("ticketPrice", price);

    if (photo) {
        formData.append("photo", photo);  
    }

    const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/projections/add/new-projection`, formData, {
        headers: getHeaderForFormData() 
    });

    if (response.status === 201) {
        return true;
    } else {
        return false;
    }
};

export async function updateProjection(projectionId, projectionData){
    const formData = new FormData();
    formData.append("movie",projectionData.movie);
    formData.append("projectionType",projectionData.projectionType);
    formData.append("hall",projectionData.hall);
    formData.append("date",projectionData.date);
    formData.append("price",projectionData.price);
    const response = await api.put(`${import.meta.env.VITE_API_BASE_URL}/projections/update/${projectionId}`, projectionData,{
        headers: getHeader()
    });
    return response;
}

export async function getProjectionById(projectionId){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/projections/projection/${projectionId}`)
        return response.data;
    }
    catch(error){
        throw new Error(`Error fetching projections : ${error.message}`);
    }
}

export async function getProjectionTypes(){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/projections/projection/projectionType`);
        return result.data;
    }
    catch(error){
        throw new Error("Error fetching projection types");
    }
}

export async function getAllProjections(){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/projections/all-projections`);
        return response.data;
    }
    catch(error){
        //console.error("Error fetching projections:", error);
		throw new Error(`Error fetching projections : ${error.message}`)
    }
}

export async function getAllProjectionTypes(){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/projectionTypes/all-types`);
        return response.data;
    }
    catch(error){
        console.error("Error fetching projection types:", error);
		throw new Error("Error fetching projection types")
    }
}

export async function getAvailableSeatsByHallId(hallId) {
    try {
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/seats/available/${hallId}`);
        return result.data;
    } catch (error) {
        throw new Error(`Error fetching available seats: ${error.message}`);
    }
}

export async function createSeat(seatNumber,available,hallId) {
    const formData = new FormData();
    formData.append("seatNumber",seatNumber);
    formData.append("available",available);
    formData.append("hallId",hallId);
    const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/seats/add/new-seat`,formData,{
        headers:getHeader()
    })
    if(response.status === 201){
        return true;
    }
    else{
        return false;
    }
}

export async function getSeats(){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/seats/all-seats`);
        return response.data;
    }
    catch(error){
        throw new Error(`Error fetching seats : ${error.message}`);
    }
}

export async function getAvailableSeats(projectionId){
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/tickets/available-seats/${projectionId}`);
        return result.data;
    }
    catch(error){
        throw new Error(`Error fetching available seats : ${error.message}`);
    }
}

export async function getSeatsByProjectionId(projectionId) {
    try{
        const result = await api.get(`${import.meta.env.VITE_API_BASE_URL}/seats/by-projection/${projectionId}`);
        return result.data;
    }
    catch(error){
        throw new Error(`Error fetching seats fro projection:  ${error.message}`);
    }
}

const getSeatsByHall = async (hallId) => {
    try {
      const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/seats/by-hall/${hallId}`);
      setSeats(response.data);
    } catch (error) {
      console.error('Greška pri dohvatanju sedišta:', error);
    }
  };

export async function createSeatsForHall(hallId, count) {
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/seats/add/seats-for-hall/${hallId}/${count}`,{
            headers: getHeader()
        })
        return response.data;
    }
    catch(error){
        throw new Error(`Error while creating seats for the hall ${error.message}`);
    }
}  

export async function getAllHalls(){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/halls/all-halls`);
        return response.data;
    }
    catch(error){
        console.error("Error fetching halls:", error);
		throw new Error("Error fetching halls")
    }
}

export async function deleteProjection(projectionId){
    try{
        const response = await api.delete(`${import.meta.env.VITE_API_BASE_URL}/projections/delete/projection/${projectionId}`,{
            headers: getHeader()
        });
        return response.data;
    }
    catch (error) {
		throw new Error(`Error deleting projection ${error.message}`)
	}
}

/*funkcija za registraciju, kao parametar, prosledjuje se registration objekat */
export async function registerUser(registration){
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/auth/register-user`, registration);
        return response.data
    }
    catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data);
        }
        else{
            throw new Error(`User registration error : ${error.message}`);
        }
    }
}

/*funkicja za logovanje, kao parametar se prosledjuje login objekat */
export async function loginUser(login){
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/auth/login` , login);
        if(response.status >= 200 && response.status < 300){
            return response.data
        }
        else{
            return null;
        }
    }
    catch(error){
        console.error(error);
        return null;
    }
}

/*ova funkcija je za dobavljanje korisnika. Kao parametar se prosledjuje id korisnika i njegov token */
export async function getUserProfile(userId, token){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/users/profile/${userId}`, {
            headers : getHeader()
        })
        return response.data;
    }
    catch(error){
        throw error;
    }
}

/*funkicja za brisanje korisnika */
export async function deleteUser(userId){
    try{
        const response = await api.delete(`${import.meta.env.VITE_API_BASE_URL}/users/delete/${userId}`,{
            headers : getHeader()
        })        
        return response.data;
    }
    catch(error){
        return error.message;
    }
}

/*funkcija koja dobavlja jednog korisnika*/
export async function getUser(userId, token){
    try{
        const response = await api.get(`${import.meta.env.VITE_API_BASE_URL}/users/${userId}`,{
            headers : getHeader()
        });
        return response.data;
    }
    catch(error){
        throw error;
    }
}


export async function processPayment(amount, currency) {
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/payments/process`,{
            amount,currency
        },{headers: getHeader()})
        return response.data
    }
    catch(error){
        console.error("Error with paying process: ", error.message)
        throw new Error("Payment failed");
    }
}

export async function createPaymentIntent(amount, currency="usd"){
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/stripe/create-payment-intent`,{
            amount,currency
        },{headers:getHeader()})
        return response.data.clientSecret;
    }
    catch(error){
        console.error("Error with creating payment intent", error.message);
        throw new Error("Error creating payment intent.");
    }
}

/*function for send regular email, text, receiver, title */
export async function sendEmai(emailRequest) {
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/email/send`,emailRequest,{
            headers:getHeader()
        })
        return response.data;
    }
    catch(error){
        console.error("Error sending email. ",error);
        throw error;
    }
}

/*example
{
    to: "nekome@email.com",
    subject: "Naslov mejla",
    text: "Ovo je sadržaj mejla"
}
*/

//sending email with file
export async function sendEmailWithAttachment(to, subject, text, file) {
    const formData = new FormData();
    formData.append("to",to);
    formData.append("subject",subject);
    formData.append("text",text);
    formData.append("file",file);
    try{
        const response = await api.post(`${import.meta.env.VITE_API_BASE_URL}/email/send-with-attachment"`,formData,{
            headers:{
                ...getHeader(),
                "Content-Type": "multipart/form-data"
            }
        })
        return response.data;
    }
    catch (error) {
        console.error("Error sending email with attachment:", error);
        throw error;
    }
}



