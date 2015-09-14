DELETE FROM loanapplicationdata;
DELETE FROM userdata;
INSERT INTO loanapplicationdata(
            id, approved, loanamount, name, personalid, surname, term)
    VALUES (1, 0, 10, 'woo', 'woo-100-poo', 'poo', '1 month');
INSERT INTO loanapplicationdata(
            id, approved, loanamount, name, personalid, surname, term)
    VALUES (2, 1, 10.1, 'za', 'za-200-zu', 'zu', '2 month');
INSERT INTO loanapplicationdata(
            id, approved, loanamount, name, personalid, surname, term)
    VALUES (3, 1, 20.3, 'pam', 'pam-300-sam', 'sam', '1 month');
INSERT INTO loanapplicationdata(
            id, approved, loanamount, name, personalid, surname, term)
    VALUES (4, 1, 100, 'pam', 'pam-300-sam', 'sam', '1 year');

INSERT INTO userdata(
            id, locked, name, surname, personalid)
    VALUES (1, 1, 'kung-fu', 'panda', 'kung-fu-666-panda');