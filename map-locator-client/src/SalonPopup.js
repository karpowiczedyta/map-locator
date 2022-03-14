import Popup from "vector-maps/dist/features/Popup";
import Marker from "vector-maps/dist/features/Marker";
import { Input } from "tombac";
import { useState } from "react";
import { url } from "./Constant";

export const SalonPopup = ({
  salon,
  opinion,
  setOpinion,
  setChangedOpinion,
}) => {
  const [open, setOpen] = useState(false);
  const [bsId, setBsId] = useState(0);

  return (
    <>
      {open && (
        <Popup
          offset={[0, 5]}
          tooltipPosition="bottom"
          tipSize={5}
          lng={salon.geometry.coordinates[0]}
          lat={salon.geometry.coordinates[1]}
          content={
            <>
              <h2>{salon.properties.typeOfService}</h2>
              <h3>{salon.properties.nameOfSalon}</h3>
              <h3>
                <link
                  rel="stylesheet"
                  href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
                  integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
                  crossorigin="anonymous"
                />
                <i class="fas fa-star"></i> <i class="fas fa-star"></i>{" "}
                <i class="fas fa-star"></i>
                {"   " + salon.properties.opinion}
              </h3>
              <Input
                onChange={(e) => setOpinion(e.target.value)}
                placeholder="Enter opinion"
                width="300px"
                type="number"
                min="1"
                max="5"
                id="input"
              />{" "}
              <button
                class="add-opinion"
                onClick={() => {
                  async function load() {
                    if (opinion !== 0.0 && bsId !== 0 && opinion <= 5.0) {
                      const response = await fetch(`${url}opinion`, {
                        method: "POST",
                        credentials: "include",
                        body: JSON.stringify({
                          id: 0,
                          opinion: opinion,
                          beautifingServiceId: bsId,
                        }),
                        headers: {
                          "Content-Type": "application/json",
                        },
                      });
                      let json = [];
                      setOpinion(0);

                      if (response.ok) {
                        json = await response.json();
                        document.getElementById("input").value = "";
                        setChangedOpinion(1);
                      } else {
                        alert();
                      }
                    }
                  }
                  load();
                }}
              >
                Dodaj opinie
              </button>
              <div class="setHidden">Dodano opinie</div>
            </>
          }
          onPopupClose={() => {
            setOpen(false);
          }}
          onClickOutside={() => {
            setOpen(false);
          }}
          closeButton
        />
      )}

      <Marker
        key={salon.properties.id}
        lng={salon.geometry.coordinates[0]}
        lat={salon.geometry.coordinates[1]}
        onMouseLeave={(e) => console.log(e)}
        onClick={(e) => {
          e.originalEvent.stopPropagation();
          console.log(e);
          setOpen(true);
          setBsId(salon.properties.id);
        }}
      />
    </>
  );
};

export default SalonPopup;
