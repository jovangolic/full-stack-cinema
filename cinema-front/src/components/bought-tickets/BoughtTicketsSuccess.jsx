import React from "react"
import { Link, useLocation } from "react-router-dom"
import Header from "../common/Header"

const BoughtTicketsSuccess = () => {

    const location = useLocation()
	const message = location.state?.message
	const error = location.state?.error
	return (
		<div className="container">
			<Header title="Ticket purchase successful" />
			<div className="mt-5">
				{message ? (
					<div>
						<h3 className="text-success"> Ticket purchase successful!</h3>
						<p className="text-success">{typeof message === "object" ? message.message : message}</p>
					</div>
				) : (
					<div>
						<h3 className="text-danger"> Error Purchasing Ticket!</h3>
						<p className="text-danger">{typeof error === "object" ? error.message : error}</p>
					</div>
				)}
			</div>
		</div>
	)
};

export default BoughtTicketsSuccess;