import React from "react";
import SalonPopup from "./SalonPopup";

function Markers({ salons, opinion, setOpinion, setChangedOpinion }) {
  return (
    <>
      {salons &&
        salons.map((salon) => (
          <SalonPopup
            key={salon.properties.id}
            salon={salon}
            opinion={opinion}
            setOpinion={setOpinion}
            setChangedOpinion={setChangedOpinion}
          />
        ))}
    </>
  );
}

export default Markers;
