import React from 'react';
import PaymentForm from './PaymentForm';
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";


const stripePromise = loadStripe("sk_test_4eC39HqLyjWDarjtT1zdp7dc");
const Checkout = () => {
  const [clientSecret, setClientSecret] = useState("");

  useEffect(() => {
    const fetchClientSecret = async () => {
      try {
        const secret = await createPaymentIntent(1000, "usd"); // 1000 = 10.00 USD
        setClientSecret(secret);
      } catch (error) {
        console.error("Adding clientSecret failed:", error);
      }
    };

    fetchClientSecret();
  }, []);

  const appearance = {
    theme: "stripe",
  };

  const options = {
    clientSecret,
    appearance,
  };

  return (
    <div>
      <h2>Checkout</h2>
      {clientSecret ? (
        <Elements stripe={stripePromise} options={options}>
          <PaymentForm />
        </Elements>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Checkout;
