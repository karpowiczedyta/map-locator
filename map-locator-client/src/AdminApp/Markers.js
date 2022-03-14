import Popup from "vector-maps/dist/features/Popup";
import Marker from "vector-maps/dist/features/Marker";
import React, { useState } from "react";
import SalonPopup from "./SalonPopup";

function Markers({ salons, setSalons }) {
  const [open, setOpen] = useState({ id: 0, open: false });
  const [openID, setOpenID] = useState(0);

  return (
    <>
      {salons &&
        salons.map((salon) => (
          <SalonPopup
            key={salon.properties.id}
            salon={salon}
            setSalons={setSalons}
          />
        ))}
    </>
  );
}

export default Markers;
