package roomescape.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.theme.Theme;
import roomescape.time.Time;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        final Reservation reservation = new Reservation(reservationRequest.name(),
                                                        reservationRequest.date(),
                                                        new Time(reservationRequest.time()),
                                                        new Theme(reservationRequest.theme(), ""));

        reservationRepository.save(reservation);

        return new ReservationResponse(reservation.getId(), reservationRequest.name(), reservation.getTheme().getName(), reservation.getDate(), reservation.getTime().getValue());
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                                    .map(it -> new ReservationResponse(it.getId(), it.getName(), it.getTheme().getName(), it.getDate(), it.getTime().getValue()))
                                    .toList();
    }
}
