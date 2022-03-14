import { Modal, Text, TombacApp } from "tombac";
import { useState } from "react";
import Details from "./Details";

export const ShowDetails = ({ salon }) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <TombacApp theme={{ settings: { modalZIndex: 10 } }}>
      <button class="showDetailsButton" onClick={() => setIsOpen(true)}>
        <i class="far fa-eye show"></i>
      </button>
      <Modal isOpen={isOpen} onRequestClose={() => setIsOpen(false)}>
        <Text padding={3}>
          <h2>Details</h2>
          <h3>
            {salon.properties.typeOfBeautifingService.map((bs) => (
              <Details bs={bs} />
            ))}
          </h3>
        </Text>
      </Modal>
    </TombacApp>
  );
};

export default ShowDetails;
